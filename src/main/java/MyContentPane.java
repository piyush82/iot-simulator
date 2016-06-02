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


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class MyContentPane extends JPanel
{
    private int locX = 0;
    private int locY = 0;
    private int objectWidth = 10;
    private int objectHeight = 10;
    private int sensorWidth = 4;
    private int sensorHeight = 4;
    private boolean showGrid = false;
    private int preferredWidth = 799;
    private int preferredHeight = 500;
    private int gridInterval = 10;
    private int influenceRange = 20;
    private Image img;
    private String imagePath = "";
    private ArrayList<SensorNode> sensorList;

    public MyContentPane()
    {
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                moveObject(e.getX(),e.getY());
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                moveObject(e.getX(),e.getY());
            }
        });
    }

    private void moveObject(int x, int y)
    {
        int OFFSET = 1;
        if (x >=0 && x < preferredWidth && y >=0 && y < preferredHeight && ((locX != x) || (locY != y)))
        {
            repaint(locX - (objectWidth/2) - OFFSET - influenceRange, locY - (objectHeight/2) - OFFSET - influenceRange, objectWidth + OFFSET + 1 + influenceRange, objectHeight + OFFSET + 1 + influenceRange);
            locX = x;
            locY = y;
            repaint(locX - (objectWidth/2) - OFFSET - influenceRange, locY - (objectHeight/2) - OFFSET - influenceRange, objectWidth + OFFSET + 1 + influenceRange, objectHeight + OFFSET + 1 + influenceRange);
            repaint(preferredWidth - 70, preferredHeight - 10, 70, 10);
        }
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(preferredWidth, preferredHeight);
    }

    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        if(imagePath.length() > 2)
            img = new ImageIcon(imagePath).getImage();
        else
            img = null;

        if(img != null)
            g.drawImage(img, 0, 0, null);

        if(showGrid)
        {
            int x = gridInterval;
            while(x < preferredWidth)
            {
                g.setColor(Color.lightGray);
                g.drawLine(x, 0, x, preferredHeight);
                x += gridInterval;
            }
            int y = gridInterval;
            while(y < preferredHeight)
            {
                g.setColor(Color.lightGray);
                g.drawLine(0, y, preferredWidth, y);
                y += gridInterval;
            }
        }

        //now populating all the sensors
        if(sensorList != null)
        {
            for(int i=0; i< sensorList.size(); i++)
            {
                SensorNode obj = sensorList.get(i);
                obj.collectorX = locX;
                obj.collectorY = locY;

                if(((obj.x - locX)*(obj.x - locX) + (obj.y - locY)*(obj.y - locY)) == influenceRange*influenceRange)
                {
                    obj.sensorState = "orange";
                    obj.activateComm();
                    g.setColor(Color.orange);
                }
                else if(((obj.x - locX)*(obj.x - locX) + (obj.y - locY)*(obj.y - locY)) <= (influenceRange*(2f/3f))*(influenceRange*(2f/3f)))
                {
                    obj.sensorState = "green";
                    obj.activateComm();
                    g.setColor(Color.green);
                }
                else if(((obj.x - locX)*(obj.x - locX) + (obj.y - locY)*(obj.y - locY)) < influenceRange*influenceRange)
                {
                    obj.sensorState = "yellow";
                    obj.activateComm();
                    g.setColor(Color.yellow);
                }
                else
                {
                    if(!obj.sensorState.equalsIgnoreCase("black"))
                        obj.lastContact = System.currentTimeMillis();
                    obj.sensorState = "black";
                    obj.deactivateComm();
                    g.setColor(Color.darkGray);
                }
                g.fillRect(obj.x - (sensorWidth/2), obj.y - (sensorHeight/2), sensorWidth, sensorHeight);
                g.setColor(Color.BLACK);
                g.drawRect(obj.x - (sensorWidth/2), obj.y - (sensorHeight/2), sensorWidth, sensorHeight);
            }
        }

        g.setColor(Color.blue);
        int x = locX;
        int y = locY;
        g.drawOval(locX - influenceRange, locY - influenceRange, influenceRange*2, influenceRange*2);

        g.setColor(Color.black);
        g.drawString("(" + locX + ", " + locY + ")", preferredWidth - 70, preferredHeight - 10);

        g.setColor(Color.blue);
        g.fillRect(locX - (objectWidth/2), locY - (objectHeight/2),objectWidth,objectHeight);
        g.setColor(Color.BLACK);
        g.drawRect(locX - (objectWidth/2), locY - (objectHeight/2),objectWidth,objectHeight);

    }

    public void setImagePath(String path)
    {
        imagePath = path;
    }

    public void setDefaultImagePath()
    {
        //ClassLoader loader = Thread.currentThread().getContextClassLoader();
        //imagePath = loader.getResource("background.jpg").getFile();
        //System.out.println(imagePath);
        //imagePath = getClass().getClassLoader().getResource("background.png").getFile();
        imagePath = "src/main/resources/background.png";
    }

    public void showGridLines()
    {
        showGrid = true;
    }

    public void hideGridLines()
    {
        showGrid = false;
    }

    public void setModelType(String model, int count, ArrayList<SensorNode> set)
    {
        switch (model) {
            case "random":
                if(count > 0)
                {
                    sensorList = new ArrayList<SensorNode>(count);
                    Random rand = new Random();
                    for(int i=0; i<count; i++)
                    {
                        int x = rand.nextInt(preferredWidth);
                        int y = rand.nextInt(preferredHeight);
                        String uuid = UUID.randomUUID().toString();
                        SensorNode temp = new SensorNode();
                        temp.id = uuid;
                        temp.x = x;
                        temp.y = y;
                        temp.initialize(1000, 1, 1, 1500L);
                        //set the IP to where rabbitmq is running, for docker look for docker-bridge IP ex 172.17.0.1
                        temp.setCommunicator(ConnectionType.RABBITMQ, "localhost", 5672, new String[]{"guest", "guest", "cyclops-test", "/"});
                        sensorList.add(i, temp);
                    }
                }
                break;
            case "source":
                break;
            default:
                sensorList = null;
        }
    }
}
