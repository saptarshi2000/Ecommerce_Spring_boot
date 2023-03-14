package com.saptarshi.ecommerce.service.Impl;

import com.saptarshi.ecommerce.dto.ListRestResponse;
import com.saptarshi.ecommerce.dto.RestResponse;
import com.saptarshi.ecommerce.exception.CustomException;
import com.saptarshi.ecommerce.model.*;
import com.saptarshi.ecommerce.repository.*;
import com.saptarshi.ecommerce.service.CustomerService;
import com.saptarshi.ecommerce.util.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;


@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final ElasticSearchService elasticSearchService;

    private final CartItemRepository cartItemRepository;

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    private final PaymentRepository paymentRepository;

    private final AddressRepository addressRepository;

    @Override
    public RestResponse regAsCustomer(Customer customer) {

        if(customerRepository.findByUserEmail(customer.getUserEmail()).isPresent()){
            return new RestResponse("already regged",HttpStatus.BAD_REQUEST);
        }

        customerRepository.save(customer);
        return new RestResponse("success",HttpStatus.CREATED);
    }

    @Override
    public RestResponse addAddress(String email,Address address) {
        Customer customer = customerRepository.findByUserEmail(email).orElseThrow();
//        List<Address> addresses = new ArrayList<>();
//        addresses.add(address);
//        customer.setAddress(addresses);
//        customerRepository.save(customer);
        address.setCustomer(customer);
        addressRepository.save(address);
        return new RestResponse("address created",HttpStatus.CREATED);
    }

    @Override
    public RestResponse deleteAddress(long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow();
        address.getCustomer().setAddress(address.getCustomer().getAddress().stream().filter(address1 -> address1.getAddress_id()!=addressId).toList());
        addressRepository.deleteById(addressId);
        return new RestResponse("address deleted",HttpStatus.OK);
    }

    @Override
    public ListRestResponse getAllAddress(String email) {
        Customer customer = customerRepository.findByUserEmail(email).orElseThrow();

        return new ListRestResponse("all address",HttpStatus.OK,customer.getAddress());
    }

    @Override
    public ListRestResponse findProducts(String query, int minValue, int maxValue,String color) {
        List<ElasticProduct> products;
        try {
            products = elasticSearchService.search(query, minValue, maxValue, color);
        }catch (IOException exception){
            throw new CustomException("error");
        }
        ListRestResponse response = ListRestResponse.builder()
                .list(products)
                .msg("success")
                .status(HttpStatus.OK)
                .build();
        return response;
    }

    @Override
    public RestResponse addToCart(String email,long productId) {
        Customer customer = customerRepository.findByUserEmail(email).orElseThrow(() ->
                new CustomException("customer not registered"));
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new CustomException("product not found"));
        Cart cart;
        List<CartItem> cartItems;
        if(customer.getCart() == null){

            cartItems = new ArrayList<>();
            cart = new Cart();
//            CartItem cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(1);
//            cartItems.add(cartItem);
//            cart.setCartItem(cartItems);
//            customer.setCart(cart);
//            customerRepository.save(customer);
        }else {
            cart = customer.getCart();
            cartItems = cart.getCartItem();
            for(CartItem cartItem: cartItems){
                if(cartItem.getProduct().getProduct_id()==productId){
                    cartItem.setQuantity(cartItem.getQuantity()+1);
                    cartItem.setCart(cart);
                    cartItemRepository.save(cartItem);
                    product.setStock(product.getStock()-1);
                    productRepository.save(product);
                    return new RestResponse("item added",HttpStatus.CREATED);
                }
            }
//            CartItem cartItem = new CartItem();
//            cartItem.setProduct(product);
//            cartItem.setQuantity(1);
//            cartItems.add(cartItem);
//            cart.setCartItem(cartItems);
//            customer.setCart(cart);
//            customerRepository.save(customer);
        }
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setCart(cart);
        cartItems.add(cartItem);
        cart.setCartItem(cartItems);
        cart.setCustomer(customer);
        product.setStock(product.getStock()-1);

        //customerRepository.save(customer);
        cartRepository.save(cart);
        productRepository.save(product);
        //cartItemRepository.save(cartItem);
        return new RestResponse("item added",HttpStatus.CREATED);
    }

    @Transactional
    @Override
    public RestResponse increaseQuantity(String email,long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
            new CustomException("cartItem not found")
                );

        cartItem.setQuantity(cartItem.getQuantity()+1);
        cartItemRepository.save(cartItem);
        Product product = cartItem.getProduct();
        product.setStock(product.getStock()-1);
        productRepository.save(product);
        return new RestResponse("item added",HttpStatus.OK);
    }

//    @Cascade(CascadeType.ALL)
    @Override
    public RestResponse decreaseQuantity(long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() ->
                new CustomException("cartItem not found")
        );

        cartItem.setQuantity(cartItem.getQuantity()-1);
        Product product = cartItem.getProduct();
        product.setStock(product.getStock()+1);
        if(cartItem.getQuantity()==0){
            System.out.println("test");
            cartItemRepository.delete(cartItem);
            if(cartItem.getCart().getCartItem().size()==0){


//                Customer customer = customerRepository.findById(cartItem.getCart().getCustomer().getCustomer_id()).orElseThrow();
//                customer.setCart(null);
//                customerRepository.save(customer);
//                long cartId = cartItem.getCart().getCartId();
                cartItem.getCart().getCustomer().setCart(null);
                //cartItem.getCart().setCustomer(null);
                //cartRepository.save(cartItem.getCart());
                cartRepository.deleteById(cartItem.getCart().getCartId());
                System.out.println(cartItem.getCart().getCartId());
            }
        }else {
            cartItemRepository.save(cartItem);
        }
        productRepository.save(product);
        return new RestResponse("item removed",HttpStatus.OK);
    }

    //@Transactional
    @Override
    public RestResponse removeFromCart( long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow();
        Product product = cartItem.getProduct();
        product.setStock(product.getStock()+cartItem.getQuantity());
        productRepository.save(product);
        cartItemRepository.deleteById(cartItemId);
        if(cartItem.getCart().getCartItem().size()==0){
            cartItem.getCart().getCustomer().setCart(null);
            cartRepository.deleteById(cartItem.getCart().getCartId());
        }
        return new RestResponse("item removed",HttpStatus.OK);
    }

    @Override
    public RestResponse order(long cartId,long addressId) {
        Address address = addressRepository.findById(addressId).orElseThrow();
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        float totalPrice =0;

        for(CartItem item: cart.getCartItem()){
            totalPrice += item.getProduct().getProductPrice()*item.getQuantity();
        }

        Payment payment = Payment.builder()
                .paymentDate(LocalDate.now())
                .paymentStatus(PaymentStatus.SUCCESS)
                .paymentValue((int) totalPrice)
                .build();
        Order order = Order.builder()
                .orderDate(LocalDate.now())
                .address(address)
                .cart(cart)
                .payment(payment)
                .active(true)
                .build();
        Order o =orderRepository.save(order);
        Customer c= customerRepository.findById(cart.getCustomer().getCustomer_id()).orElseThrow();
        List<Order> orders;
        if(c.getOrders().size()==0){
            orders = new ArrayList<>();
        }else {
            orders = c.getOrders();
        }
        orders.add(o);
        c.setOrders(orders);
        customerRepository.save(c);
        cart.setCustomer(null);
        cartRepository.save(cart);
        //cartRepository.deleteById(cartId);

        return new RestResponse("ordered",HttpStatus.OK);
    }

    @Override
    public RestResponse cancelOrder(long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.setActive(false);
        orderRepository.save(order);
        return new RestResponse("order canceled",HttpStatus.OK);
    }

    @Override
    public ListRestResponse getAllOrder(String email){
        return new ListRestResponse("ok",HttpStatus.OK,customerRepository.findByUserEmail(email).orElseThrow().getOrders());
    }
}
