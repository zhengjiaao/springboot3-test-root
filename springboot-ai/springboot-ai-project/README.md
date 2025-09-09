# springboot-test-root

> [springboot 3.x 官方文档](https://spring.io/projects/spring-boot)

**说明**

springboot 项目中常用到的一些技术集成示例，可以快速开始开箱测试，以及集成到项目当中使用。

## spring-boot 集成(组件)示例

> 以下是已经完成的示例模块

- [springboot-ai-package AI集成组件](./springboot-ai-package)
    - [springboot-ai-alibaba 阿里云百炼-大模型AI](springboot-ai-package/springboot-ai-alibaba)
    - [springboot-ai-asr ASR语音识别](springboot-ai-package/springboot-ai-asr)
    - [springboot-ai-chatgpt 接入OpenAI](springboot-ai-package/springboot-ai-chatgpt)
    - [springboot-ai-langchain4j 大模型语言统一API封装](springboot-ai-package/springboot-ai-langchain4j)
    - [springboot-nlp NLP自然语言分析](springboot-ai-package/springboot-ai-nlp)
    - [springboot-ocr OCR图文识别提取](springboot-ai-package/springboot-ai-ocr)
- [springboot-ai-project AI集成项目](./springboot-ai-project)

## 本地运行环境搭建

> 以下是你必须要安装的基础软件,可以使项目正常打包及运行.

|       | 官网文档                                                                                | github | 使用版本下载                                                                         | 详细 | 是否必须安装 |
|-------|-------------------------------------------------------------------------------------|--------|--------------------------------------------------------------------------------|----|--------| 
| java  | [www.oracle.com/java17](https://www.oracle.com/java/technologies/downloads/#java17) |        | [java17 downloads](https://www.oracle.com/java/technologies/downloads/#java17) |    | **必须** |
| maven | [maven.apache.org](https://maven.apache.org/)                                       |        | [maven3.6.2 downloads](https://maven.apache.org/download.cgi)                  |    | **必须** |

## 适配的中间件版本

> 以下是你可能会用到的中间件

|           | 官网文档                                                              | github | 使用版本下载                                                                                                                          | 详细 | 推荐 |
|-----------|-------------------------------------------------------------------|--------|---------------------------------------------------------------------------------------------------------------------------------|----|----| 
| zookeeper | [zookeeper.apache.org](http://zookeeper.apache.org/releases.html) |        | [zookeeper-3.6.3-bin.tar.gz](https://www.apache.org/dyn/closer.lua/zookeeper/zookeeper-3.6.3/apache-zookeeper-3.6.3-bin.tar.gz) |    |    |
| Git       | [git-scm.com](https://git-scm.com/)                               |        | [git-latest](https://git-scm.com/downloads)                                                                                     |    |    |

## 后续计划

> 以下是后续计划预研的技术，不分先后顺序

2025年计划：

|               | 说明                            | 是否完成 |
|---------------|-------------------------------|------|
| springboot-ai | 预研Ai相关技术(ChatGPT、ChatGLM、...) | 进行中  |

## 你还可以学习其他项目

> 以下是你可能需要学习的其他项目及技术

|                                          | 资源地址                                                                                         | 说明                                                                                 |  |
|------------------------------------------|----------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|--|
| github/zhengjiaao                        | [github.com/zhengjiaao](https://github.com/zhengjiaao)                                       | 主页面，展示一些比较重要技术预研项目                                                                 |  |
| zhengjiaao/springboot-test-root          | [springboot-test-root](https://github.com/zhengjiaao/springboot-test-root)                   | springboot 2.x 技术预研框架,内容较多，较基础，偏向于技术的应用，适合初学者快速掌握某项技术，欢迎Star，推荐学习                  |  |
| zhengjiaao/springcloud-test-root         | [springcloud-test-root](https://github.com/zhengjiaao/springcloud-test-root)                 | springcloud 全家桶(组件) 技术预研框架,内容较多，较基础，偏向于技术的应用，适合初学者快速掌握某项技术，欢迎Star，推荐学习             |  |
| zhengjiaao/spring-boot-starter-test-root | [spring-boot-starter-test-root](https://github.com/zhengjiaao/spring-boot-starter-test-root) | spring-boot-starter 2.x 全家桶(组件) 技术预研框架,内容较多，较基础，偏向于技术的应用，适合初学者快速掌握某项技术，欢迎Star，推荐学习 |  |

## springboot 版本对应选型

- 参考地址：
    - https://start.spring.io/actuator/info
    - https://spring.io/projects/spring-cloud#overview
    - https://docs.spring.io/spring-boot/docs/{springboot-verion}/reference/htmlsingle/

| Spring Boot   | Spring Framework | Spring Cloud             | spring-cloud-alibaba | Java        | Maven          | Gradle                     | Tomcat                   |
|---------------|------------------|--------------------------|----------------------|-------------|----------------|----------------------------|--------------------------|
| 3.5.x         | 6.0.x            | 2025.0.x aka Northfields | 2025.x               | Java 17     | 3.6.3 or later | 7.x (7.5 or later) and 8.x | Tomcat 10.x              |
| 3.4.x         | 6.0.x            | 2024.0.x aka Moorgate    | 2024.x               | Java 17     | 3.6.3 or later | 7.x (7.5 or later) and 8.x | Tomcat 10.x              |
| 3.3.x         | 6.0.x            | 2023.0.x aka Leyton      | 2023.x               | Java 17     | 3.6.3 or later | 7.x (7.5 or later) and 8.x | Tomcat 10.x              |
| 3.2.x         | 6.0.x            | 2023.0.x aka Leyton      | 2023.x               | Java 17     | 3.6.3 or later | 7.x (7.5 or later) and 8.x | Tomcat 10.x              |
| 2.7.x         | 5.3.x            | 2021.0.x                 | 2021.x               | Java 8 or 9 | 3.5+           | 6.8+                       | Tomcat 9.x               |
| 2.5.x         | 5.3.x            | 2020.0.x                 | 2020.x               | Java 8 or 9 | 3.5+           | 6.8+                       | Tomcat 8.x or Tomcat 9.x |
| 2.3.x.RELEASE | 5.2.x.RELEASE    | Hoxton                   | 2.2.x                | Java 8 or 9 | 3.3+           | 4.4+                       | Tomcat 8.x or Tomcat 9.x |
| 1.5.x.RELEASE | 4.x.x.RELEASE    | Edgware                  | 1.5.x                | Java 7 or 8 | 3.2+           | 2.9+                       | Tomcat 7.x or Tomcat 8.x |
