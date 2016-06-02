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

## need more info or want to contribute
Please contact me at ```piyush DOT harsh AT zhaw DOT ch```.

## License

this software is licensed under the
[Apache License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
See the file LICENSE.

## Made by

<div align="left" >
<a href='http://blog.zhaw.ch/icclab'>
<img src="https://raw.githubusercontent.com/icclab/hurtle/master/docs/figs/icclab_logo.png" title="IoT simulator" width=248px>
</a>
</div>