# Mockito Sugar 

[![Build Status](https://travis-ci.org/dokwork/mockito-sugar.svg?branch=master)](https://travis-ci.org/dokwork/mockito-sugar) [ ![Download](https://api.bintray.com/packages/dokwork/maven/mockito-sugar/images/download.svg) ](https://bintray.com/dokwork/maven/mockito-sugar/_latestVersion)
 
This project provides syntax sugar for methods from [Mockito](http://site.mockito.org) project.

## Install

Add this to your sbt project:
```scala
resolvers += Resolver.jcenterRepo // only for sbt before 0.13.6
libraryDependencies += "ru.dokwork"  %% "mockito-sugar" % "<version>" % "test"
```

This library doesn't provide any dependencies. It's mean that your should add
`mockito` library explicitly:
```scala 
libraryDependencies += "org.mockito" % "mockito-core" % "2.x.x" % "test"
```
If you wish to use the sugar for `scalatest` your should add it explicitly too:
```scala 
libraryDependencies += "org.scalatest" %% "scalatest" % "3.x.x" % "test"
```

## Features

* Short versions for methods with argument of `Class[T]`:

    for example instead use `org.mockito.Mockito#mock(java.lang.Class)` directly like:
    ```scala 
    org.mockito.Mockito.mock(classOf[MyClass]) 
    ```
    you can use short version:
    ```scala
    mock[MyClass]
    ```   
    
* Sugar for `ArgumentCaptor`:
    ```scala
    "Examples for argument captor" in new MockitoSugar {
        // given:
        val list: List[Int] = mock[List[Int]]
        val captor = argumentCaptor[Int]
        // when:
        list.take(5)
        verify(list).take(captor)
        // then:
        assert(captor.value == 5)
    }
    ```    

* Sugar for argument matchers:
    ```scala
    "Example for argument matchers" in new MockitoSugar {
        // given:
        val list: List[Int] = mock[List[Int]]
        // when:
        list.slice(0, 5)
        // then:
        verify(list).slice(any[Int], argThat[Int](_ > 0))
    }
    ```
    
* Implicit  conversion functions to Answers: 
     ```scala
    "Example for implicit answer" in new MockitoSugar {
        // given:
        val list: List[Int] = mock[List[Int]]
        // when:
        doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(any[Int])
        // then:
        assert(list.take(5) == (1 to 5).toList)
    }
    ```
    Arguments for functions will be the same arguments of the method which answer used for.
    
See more examples here: [ru.dokwork.sugar.mockito.MockitoSugarExamples](/src/test/scala/ru/dokwork/sugar/mockito/MockitoSugarExamples.scala)
    

    
