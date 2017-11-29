# Mockito Sugar 

[![Build Status](https://travis-ci.org/dokwork/mockito-sugar.svg?branch=master)](https://travis-ci.org/dokwork/mockito-sugar) [ ![Download](https://api.bintray.com/packages/dokwork/maven/mockito-sugar/images/download.svg) ](https://bintray.com/dokwork/maven/mockito-sugar/_latestVersion)
 
This project provides syntax sugar for methods from [Mockito project](http://site.mockito.org).

## Install

```scala
resolvers += Resolver.jcenterRepo // only for sbt before 0.13.6
libraryDependencies += "ru.dokwork" %% "mockito-sugar" % "<version>" % "test"
```

## Features

1. Short versions for methods with argument of `Class[T]`:
    * `org.mockito.Mockito.mock`
    * `org.mockito.ArgumentMatchers.any`

    Instead use `org.mockito.Mockito#mock(java.lang.Class)` directly like:
    ```scala 
    org.mockito.Mockito.mock(classOf[MyClass]) 
    ```
    you can use method from the trait [Mockito](/src/main/scala/ru/dokwork/test/sugar/Mockito.scala):
    ```scala
    mock[MyClass]
    ```   
    
1. Simple method for create `ArgumentCaptor`:
    ```scala
    "Examples for argument captor" in new Mockito {
        // given:
        val list: List[Int] = mock[List[Int]]
        val captor = argumentCaptor[Int]
        // when:
        list.take(5)
        verify(list).take(captor.capture())
        // then:
        assert(captor.getValue == 5)
    }
    ```    

1. Sugar for argument matchers:
    ```scala
    "Example for argument matchers" in new Mockito {
        // given:
        val list: List[Int] = mock[List[Int]]
        // when:
        list.slice(0, 5)
        // then:
        verify(list).slice(*[Int], ==[Int](_ > 0))
        // or: verify(list).slice(any[Int], argThat[Int](_ > 0))
    }
    ```
    
1. Implicit  conversion functions to Answers: 
     ```scala
    "Example for implicit answer" in new Mockito {
        // given:
        val list: List[Int] = mock[List[Int]]
        // when:
        doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(*[Int])
        // then:
        assert(list.take(5) == (1 to 5).toList)
    }
    ```
    Arguments for functions will be arguments of the method which answer used for.
    
See more examples here: [ru.dokwork.test.sugar.MockitoExamples](/src/test/scala/ru/dokwork/test/sugar/MockitoExamples.scala)
    

    
