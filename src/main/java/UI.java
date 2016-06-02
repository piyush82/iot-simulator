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
import java.util.ArrayList;

public class UI
{
    private JFrame mainWindow;
    private JPanel headerPane;
    private MyContentPane mainPane;
    private JPanel footerPane;
    private JLabel footerMsg;

    public UI()
    {
        prepareGUI();
    }

    private void prepareGUI()
    {
        mainWindow = new JFrame("ICCLab IoT Simulator");
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setSize(800, 600);

        mainWindow.getContentPane().setLayout(new BoxLayout(mainWindow.getContentPane(),BoxLayout.PAGE_AXIS));

        headerPane = new JPanel();
        headerPane.setLayout(new FlowLayout());
        headerPane.setPreferredSize(new Dimension(799, 49));

        mainPane = new MyContentPane();
        //mainPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        //mainPane.setPreferredSize(new Dimension(799, 500));

        footerPane = new JPanel();
        footerPane.setLayout(new FlowLayout());
        footerPane.setPreferredSize(new Dimension(799, 49));
        footerMsg = new JLabel("(c) 2016 - ICCLab, ZHAW - Piyush Harsh", JLabel.RIGHT);
        footerMsg.setSize(300, 30);
        footerPane.add(footerMsg);

        headerPane.setBackground(Color.ORANGE);
        headerPane.setVisible(true);

        footerPane.setBackground(Color.ORANGE);
        footerPane.setVisible(true);

        mainWindow.add(headerPane);
        mainWindow.add(mainPane);
        mainWindow.add(footerPane);

        mainWindow.pack();
        mainWindow.setResizable(false);
        mainWindow.setVisible(true);
    }

    public void showGridLines()
    {
        mainPane.showGridLines();
    }

    public void setBackgroundImage(String path)
    {
        mainPane.setImagePath(path);
    }

    public void setBackgroundImage()
    {
        //mainPane.setDefaultImagePath();
        mainPane.setImagePath("src/main/resources/background1.png");
    }

    public void setPoints(String modelType, int sensorCount, ArrayList<SensorNode> sensorNodeList)
    {
        mainPane.setModelType(modelType, sensorCount, null);
    }

    public void drawPlot()
    {

    }
}
