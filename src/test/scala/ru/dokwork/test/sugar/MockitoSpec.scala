package ru.dokwork.test.sugar

import org.scalatest.FreeSpec
import org.mockito.Mockito._

class MockitoSpec extends FreeSpec {

  "Example for implicit answer" in new Mockito with Mockito.Answers {
    // given:
    val list: List[Int] = mock[List[Int]]
    // when:
    doAnswer((n: Int) ⇒ (1 to n).toList).when(list).take(*[Int])
    // then:
    assert(list.take(5) == (1 to 5).toList)
  }
}
