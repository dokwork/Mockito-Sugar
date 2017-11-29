## Mockito Sugar 
This project provides syntax sugar for methods from [Mockito project](http://site.mockito.org).

1. Short versions for methods with `Class[T]` arguments:
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

1. Sugar for argument matchers:
    ```scala
    "Example for argument matchers" in {
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
    

    