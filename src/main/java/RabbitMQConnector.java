/*
 * Copyright (c) 2016. Zuercher Hochschule fuer Angewandte Wissenschaften
 *  All Rights Reserved.
 *
 *     Licensed under the Apache License, Version 2.0 (the "License"); you may
 *     not use this file except in compliance with the License. You may obtain
 *     a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *     WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *     License for the specific language governing permissions and limitations
 *     under the License.
 */

/*
 *     Author: Piyush Harsh,
 *     URL: piyush-harsh.info
 */

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class RabbitMQConnector extends IoTConnection
{
    String queueName;
    String rabbitUser;
    String rabbitPass;
    String vHost;
    ConnectionFactory factory;

    @Override
    boolean initialize(String IP, int Port, String[] args)
    {
        this.ip = IP;
        this.port = Port;
        if(args != null && args.length > 3)
        {
            rabbitUser = args[0];
            rabbitPass = args[1];
            queueName = args[2];
            vHost = args[3];
            factory = new ConnectionFactory();
            factory.setHost(this.ip);
            factory.setPort(this.port);
            factory.setUsername(rabbitUser);
            factory.setPassword(rabbitPass);
            factory.setVirtualHost(vHost);
        }
        return true;
    }

    @Override
    boolean sendMsg(String msg)
    {
        try
        {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            AMQP.Queue.DeclareOk myQueue = channel.queueDeclare(queueName, true, false, false, null);
            String message = msg;
            channel.basicPublish("", queueName, null, message.getBytes());
            channel.close();
            connection.close();
        }
        catch(Exception ex)
        {
            return false;
        }
        return true;
    }
}
