[![New Relic Experimental header](https://github.com/newrelic/opensource-website/raw/master/src/images/categories/Experimental.png)](https://opensource.newrelic.com/oss-category/#new-relic-experimental)
  
# Atomikos Data Source Instrumentation

>Gives status of Atomikos Data Sources since this information is unavailable via JMX

## Installation

> 1. Download the Release jars   
> 2. In the New Relic Java Agent directory (the one containing newrelic.jar), create a directory named extensions   
> 3. Copy the downloaded jars into the extensions directory   
> 4. Restart the application

## Getting Started
> Once the jars have been deployed, the New Relic Java Agent should start reporting Metrics of the following format: Custom/Atomikos Connection Pools/JDBC/poolName/Available & Custom/Atomikos Connection Pools/JDBC/poolname/Total.    
> 

## Building

> To build the instrumentation jars requires having Gradle installed.   
To Build:   
Set the environment variable NEW_RELIC_EXTENSIONS_DIR to the extensions directory of the New Relic Java Agent or a local directory   
To build a single module, run the command   
gradle moduleName:clean moduleName:install   
To build all modules, run the command:   
gradle clean install
If NEW_RELIC_EXTENSIONS_DIR does not point to the extensions directory of the New Relic Java Agent, copy the resulting jars into the extensions directory   
Restart the application

## Support

New Relic hosts and moderates an online forum where customers can interact with New Relic employees as well as other customers to get help and share best practices. Like all official New Relic open source projects, there's a related Community topic in the New Relic Explorers Hub. You can find this project's topic/threads here:


## Contributing
We encourage your contributions to improve [project name]! Keep in mind when you submit your pull request, you'll need to sign the CLA via the click-through using CLA-Assistant. You only have to sign the CLA one time per project.
If you have any questions, or to execute our corporate CLA, required if your contribution is on behalf of a company,  please drop us an email at opensource@newrelic.com.
   
**A note about vulnerabilities**

As noted in our [security policy](../../security/policy), New Relic is committed to the privacy and security of our customers and their data. We believe that providing coordinated disclosure by security researchers and engaging with the security community are important means to achieve our security goals.

If you believe you have found a security vulnerability in this project or any of New Relic's products or websites, we welcome and greatly appreciate you reporting it to New Relic through [HackerOne](https://hackerone.com/newrelic).   
   
## License
Atomikos Data Source Instrumentation is licensed under the [Apache 2.0](http://apache.org/licenses/LICENSE-2.0.txt) License.
