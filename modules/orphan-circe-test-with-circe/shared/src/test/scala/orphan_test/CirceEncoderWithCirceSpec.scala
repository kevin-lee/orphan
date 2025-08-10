package orphan_test

import hedgehog.*
import hedgehog.runner.*
import io.circe.literal.*
import io.circe.syntax.*
import orphan_instance.OrphanCirceInstances.MyBox

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceEncoderWithCirceSpec extends Properties {

  override def tests: List[Test] = List(
    property("testCirceEncoder", testCirceEncoder)
  )

  def testCirceEncoder: Property = for {
    id       <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name     <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive <- Gen.boolean.log("isActive")
    myBox    <- Gen.constant(MyBox(id, name, isActive)).log("myBox")
  } yield {
    val expected =
      json"""{
               "id":$id,
               "name":$name,
               "isActive":$isActive
             }"""

    val actual = myBox.asJson
    actual ==== expected
  }

}
