# Test Utils

This project should help you to write unit tests. It will contain different helpers like:
 * [x] syntax sugar for [mockito](http://site.mockito.org/)
 * [ ] tools for generate arbitrary instances of case classes for scalacheck
 * [ ] scalatest matcher for string with json 
 
 
## Sugar for Mockito
 
 ```scala
"Example for implicit answer" in new Mockito with Mockito.Answers {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(*[Int])
    // then:
    assert(list.take(5) == (1 to 5).toList)
}
```
See [ru.dokwork.test.sugar.MockitoSpec](/src/test/scala/ru/dokwork/test/sugar/MockitoSpec.scala)