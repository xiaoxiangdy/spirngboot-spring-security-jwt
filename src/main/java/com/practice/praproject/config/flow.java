package com.practice.praproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;


@Configuration
@EnableIntegration
@MessageEndpoint
public class flow {


    //生产者 -> filter 、Router 、 Transformer -> channel -> 消费者
    //QueueChannel 点对点，先进先出通道
//    @Bean
//    public MessageChannel numberChannel(){
//
//    }

    @Bean
    public MessageChannel evenChannel(){
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel oddChannel(){
        return new PublishSubscribeChannel();
    }

    @Bean
    public MessageChannel dateChannel(){
        return new QueueChannel();
    }

    @Bean
    public MessageChannel dateToStrChannel(){
        return new QueueChannel();
    }

    @Bean
    public MessageChannel filterChannel(){
        return new QueueChannel();
    }
//    /**@InboundChannelAdapter 生产者    value：生产消息到dateChannel
//     *  poller：如果使用QueueChannel如ueueChannel通道，必须为消费者配置一个轮询器 一次（1000ms）从名为beep的通道轮询
//     */
//    @InboundChannelAdapter(value = "oddChannel",poller = @Poller(fixedRate = "1000"))
//    public Date input(){
//        System.out.println("Generate+++");
//        return new Date();
//    }
////
//    /**@ServiceActivator消费者    value：从dateChannel拿到消息
//     *  poller：如果使用QueueChannel如ueueChannel通道，必须为消费者配置一个轮询器 一次（1000ms）从名为beep的通道轮询
//     */
//    @ServiceActivator(inputChannel = "dateChannel",poller = @Poller(fixedRate = "1000"))
//    public void output(Message<?> message){
//        System.out.println("dateChannelMsg" + message);
//    }
//
//    @ServiceActivator(inputChannel = "dateToStrChannel",poller = @Poller(fixedRate = "1000"))
//    public void outputStr(Message<?> message){
//        System.out.println("dateToStrChannelMsg" + message);
//    }

//    /*
//    Transformer 转换器：消息转换器，用于转换消息内容和格式
//    将dateChannel 数据 处理之后 生产到 dateToStrChannel（个人感觉Transformer就是一种特殊的消费者）
//     */
//    @Transformer(inputChannel = "dateChannel",outputChannel = "dateToStrChannel",poller = @Poller(fixedRate = "1000"))
//    public Object romanNumTransformer(Date message){
//        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(message);
//    }

    /**
     * 路由器：从numberChannel通道拿到消息，将偶数消息定向到evenChannel 奇数定向到oddChannel
     * @param message
     * @return
     */
//    @Router(inputChannel = "numberChannel")
//    public Object evenOddRouter(Message<?> message){
//        Integer number = (Integer)message.getPayload();
//        if (number % 2 == 0){
//            return Collections.singleton(evenChannel());
//        }
//        return Collections.singleton(oddChannel());
//    }
//    @InboundChannelAdapter(value = "filterChannel",poller = @Poller(fixedRate = "1000"))
//    public int numberChannelInput(){
//        Random random = new Random();
//        int i = random.nextInt();
//        return i;
//    }
    @Bean
    @ServiceActivator(inputChannel = "evenChannel")
    public MessageHandler evenChannelOutput(){
       return message ->{System.out.println(evenChannel());
       };
    }
//
//    @ServiceActivator(inputChannel = "oddChannel")
//    public void oddChannelOutputTwo(Message<?> message){
//        System.out.println("奇数" + message);
//    }
//
    @ServiceActivator(inputChannel = "oddChannel")
    public void oddChannelOutput(Message<?> message){
        System.out.println("奇数" + message);
    }
//
//    /**
//     * 过滤器
//     * @param message
//     * @return
//     */
//    @Filter(inputChannel = "filterChannel", outputChannel = "numberChannel",poller = @Poller(fixedRate = "1000"))
//    public boolean notifyOnlyIfLastConnectionClosed(Message<?> message) {
//        Integer number = (Integer)message.getPayload();
//        System.out.println(number);
//        if (number < 10000){
//            return true;
//        }
//        return false;
//    }

//    @Bean
//    public IntegrationFlow inbound() {
//        return IntegrationFlows.from(Http.inboundGateway("/ddd")
//                .requestMapping(m -> m.methods(HttpMethod.GET))
//                .requestPayloadType(String.class))
//                .channel("evenChannel")
//                .get();
//    }
//
//    @Bean
//    public IntegrationFlow outbound() {
//        return IntegrationFlows.from("evenChannel")
//                .handle(Http.outboundGateway("http://localhost:8989/hello")
//                        .httpMethod(HttpMethod.GET)
//                        .expectedResponseType(String.class))
//                .get();
//    }


    @Bean
    public HttpRequestHandlingMessagingGateway inbound() {
        HttpRequestHandlingMessagingGateway gateway =
                new HttpRequestHandlingMessagingGateway(true);
        gateway.setRequestMapping(mapping());
        gateway.setRequestPayloadType(String.class);
        gateway.setRequestChannelName("oddChannel");
        return gateway;
    }

    @Bean
    public RequestMapping mapping() {
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setPathPatterns("/foo");
        requestMapping.setMethods(HttpMethod.GET);
        return requestMapping;
    }

    @ServiceActivator(inputChannel = "oddChannel")
    @Bean
    public HttpRequestExecutingMessageHandler outbound() {
        HttpRequestExecutingMessageHandler handler =
                new HttpRequestExecutingMessageHandler("http://localhost:8989/hello");
        handler.setHttpMethod(HttpMethod.POST);
        handler.setExpectedResponseType(String.class);
        return handler;
    }








}
