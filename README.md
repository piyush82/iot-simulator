# iot-simulator
Simple tool to simulate IoT deployments and data streams and collections

## Requirements
1. Java 8
2. Maven 3.0.5 or higher

## Compilation
```bash
mvn clean compile assembly:single
```

## Execution
```bash
java -jar target/IoT-Simulator-0.01-jar-with-dependencies.jar
```

## Notes
1. Current release has support for RabbitMQ connections only. Other connectors will be added very soon.

## Usage
Once the tool is running, simply drag the collector node through the various parts of the screen and IoT nodes in the vicinity of the collector node will offload their locally cached data to the collector.

## Philosophy & demo videos
[![IMAGE ICCLab IoT Toolkit Preview - 1](http://img.youtube.com/vi/LYZsPmeMRgw/0.jpg)](http://www.youtube.com/watch?v=LYZsPmeMRgw "ICCLab IoT Toolkit Preview - 1")

[![IMAGE ICCLab IoT Toolkit Preview - 2](http://img.youtube.com/vi/bgxdvpK8kR0/0.jpg)](http://www.youtube.com/watch?v=bgxdvpK8kR0 "ICCLab IoT Toolkit Preview - 2")

## need more info or want to contribute
Please contact me at ```piyush DOT harsh AT zhaw DOT ch```.

### Writing your own IoT transmission error model
Simply add code to the getErrorFactor method of ErrorModel class and use ErrorModel.activateCustomModel() in main method.

```java
public static float getErrorFactor(float displacement)
{
    //add your algorithm here - return value should be between 0.0 and 100.00
    //therefore please scale the return probability factor accordingly
    //input is the displace of the data collector node from sensor node

    return 0.0f;
}
```

## License

this software is licensed under the
[Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
See the file LICENSE.

## Made with love at

<div align="left" >
<a href='http://blog.zhaw.ch/icclab'>
<img src="https://raw.githubusercontent.com/icclab/hurtle/master/docs/figs/icclab_logo.png" title="IoT simulator" width=248px>
</a>
</div>

[![DOI](https://zenodo.org/badge/60262025.svg)](https://zenodo.org/badge/latestdoi/60262025)
