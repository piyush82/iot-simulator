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

import java.util.ArrayList;
import java.util.Random;

public class SensorNode implements Runnable
{
    public int x;
    public int y;
    public int collectorX;
    public int collectorY; //coordinates of the collection vehicle
    public String id;
    int dataFillPolicy; //1=if full dequeue last and enque latest, 2=if full ignore new samples
    int dataRemovalPolicy; //1=once sent remove from queue, 2=remove only once acknowledgment arrives
    long lastContact; //last time when this node offloaded data
    IoTConnection communicator; //this points to construct using which this node can send data
    ArrayList<SensorSample> dataPool;
    public String sensorState;
    private Thread t;
    long dataGenerationInterval;

    public void initialize(int poolSize, int fillPolicy, int removalPolicy, long period)
    {
        dataPool = new ArrayList<SensorSample>(poolSize);
        dataFillPolicy = fillPolicy;
        dataRemovalPolicy = removalPolicy;
        sensorState = "black"; //other values are orange, yellow and green
        lastContact = System.currentTimeMillis(); //initializing start state to when this node is deployed
        dataGenerationInterval = period;
        t = null;
    }

    public void setCommunicator(ConnectionType type, String ip, int port, String[] args)
    {
        switch(type.getValue())
        {
            case 1: //initialize rabbitmq connection
                communicator = new RabbitMQConnector();
                communicator.initialize(ip, port, args);
                break;
            default:
                communicator = null;
        }
    }

    public void deactivateComm()
    {
        if(t != null)
        {
            t.stop();
            t = null;
        }
    }

    public void activateComm()
    {
        if(t == null)
        {
            t = new Thread(this);
            t.start();
        }
    }

    @Override
    public void run()
    {
        //calculating the number of messages that is queued
        long delay = System.currentTimeMillis() - lastContact;
        long countPendingMsgs = delay / dataGenerationInterval;
        long timestamp = System.currentTimeMillis();
        Random rand = new Random(System.currentTimeMillis());
        int errorRate;
        int sendingProb;
        while(!sensorState.equalsIgnoreCase("black"))
        {
            float displacement = (float) Math.sqrt(Math.pow(collectorX - x, 2) + Math.pow(collectorY - y, 2));

            try
            {
                if(countPendingMsgs < 0)
                    timestamp = System.currentTimeMillis();
                else
                    timestamp = System.currentTimeMillis() - dataGenerationInterval*countPendingMsgs;

                String msg = "timestamp: " + timestamp + ", source-id: " + id + ", meter-id: 0x11fd, meter-class: 0xA0, value: " + rand.nextInt(128) + ", self-state: low-power, power-consumed: " + rand.nextInt(1000) + " mWatt";
                if(ErrorModel.getCustomErrorModelState())
                {
                    errorRate = (int) ErrorModel.getErrorFactor(displacement);
                }
                else //use the default error model
                {
                    if (sensorState.equalsIgnoreCase("orange"))
                        errorRate = 75;
                    else if (sensorState.equalsIgnoreCase("yellow"))
                        errorRate = 20;
                    else if (sensorState.equalsIgnoreCase("green"))
                        errorRate = 1;
                    else
                        errorRate = 100;
                }

                sendingProb = rand.nextInt(100);
                if(sendingProb > errorRate)
                {
                    communicator.sendMsg(msg);
                }
                else
                {
                    communicator.sendMsg(id + "> unreadable transmission - msg lost");
                }
                if(countPendingMsgs >= 0 ) countPendingMsgs--;
                if(countPendingMsgs < 0)
                    Thread.sleep(dataGenerationInterval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
