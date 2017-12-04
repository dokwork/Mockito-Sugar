package ru.dokwork.sugar.mockito

import org.mockito.ArgumentCaptor

import scala.collection.JavaConverters._

/**
  * Extends [[org.mockito.ArgumentCaptor]] to add short methods for
  * [[org.mockito.ArgumentCaptor#get() getValue]] and
  * [[org.mockito.ArgumentCaptor#getAll() getAllValues]].
  *
  * Provides ability to use captor as argument in the methods invocation implicitly invokes
  * method [[org.mockito.ArgumentCaptor#capture()]]:
  *
  * @example
  *  {{{
  *  // given:
  *  val list: List[Int] = mock[List[Int]]
  *  val captor = argumentCaptor[Int]
  *  // when:
  *  list.take(5)
  *  verify(list).take(captor)
  *  // then:
  *  assert(captor.last == 5)
  *  }}}
  */
trait RichArgumentCaptor {

  /**
    * Create [[ArgumentCaptor]] for type `T`.
    */
  def argumentCaptor[T: Manifest]: ArgumentCaptor[T] = ArgumentCaptor.forClass(clazz)

  implicit def capture[T](captor: ArgumentCaptor[T]): T = captor.capture()

  implicit class RichArgumentCaptor[T](captor: ArgumentCaptor[T]) {
    /**
      * Equals to [[org.mockito.ArgumentCaptor#getValue()]]
      */
    def value: T = captor.getValue

    /**
      * Equals to [[org.mockito.ArgumentCaptor#getAllValues()]], but returns [[scala.List]]
      * instead [[java.util.List]].
      */
    def values: List[T] = captor.getAllValues.asScala.toList
  }

  private def clazz[T: Manifest]: Class[T] = manifest.runtimeClass.asInstanceOf[Class[T]]
}
