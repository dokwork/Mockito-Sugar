package ru.dokwork.sugar.mockito

import org.mockito.Mockito
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.{ Answer, OngoingStubbing, Stubber }

import scala.concurrent.Future
import scala.util.Try

/**
  * Provides syntax sugar for [[Stubber]].
  */
trait RichStubber {
  /**
    * Wrap result of `f` to the Future.successful as answer.
    */
  def doReturnAsFuture[T](f: ⇒ T): Stubber = Mockito.doAnswer(new Answer[Future[T]] {
    override def answer(invocation: InvocationOnMock): Future[T] = Future.fromTry(Try(f))
  })

  implicit class RichOngoingStubbing[T](stubber: OngoingStubbing[T]) {

    /**
      * Wrap result of `f` to the Future.successful as answer.
      */
    def thenReturnAsFuture[T](f: ⇒ T) = stubber.thenAnswer(new Answer[Future[T]] {
      override def answer(invocation: InvocationOnMock): Future[T] = Future.fromTry(Try(f))
    })
  }
}
