package ru.dokwork.sugar.mockito

import org.mockito.Mockito._
import org.scalatest.AsyncFreeSpec

import scala.concurrent.Future

class RichOngoingStubbingSpec extends AsyncFreeSpec with MockitoSugar {

  "should collect answers" in {
    // given:
    val list = mock[List[Future[Int]]]
    // when:
    when(list.apply(*))
      .thenReturnAsFuture(throw new Exception())
      .thenReturnAsFuture(1)
      .thenReturnAsFuture(2)
      .thenReturnAsFuture(3)
    // then:
    list(1)
    list(1).map(i ⇒ assert(i == 1))
    list(1).map(i ⇒ assert(i == 2))
    list(1).map(i ⇒ assert(i == 3))
  }
}
