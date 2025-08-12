package orphan_test

import hedgehog.*
import hedgehog.runner.*
import io.circe.literal.*
import io.circe.parser.*
import orphan_instance.OrphanCirceInstances.MyBox

/** @author Kevin Lee
  * @since 2025-08-10
  */
object CirceDecoderWithCirceSpec extends Properties {

  override def tests: List[Test] = List(
    property("testCirceDecoder", testCirceDecoder)
  )

  def testCirceDecoder: Property = for {
    id       <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name     <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive <- Gen.boolean.log("isActive")
    myBox    <- Gen.constant(MyBox(id, name, isActive)).log("myBox")
  } yield {
    val json =
      json"""{
               "id":$id,
               "name":$name,
               "isActive":$isActive
             }"""

    val expected = myBox
    decode[MyBox](json.noSpaces).matchPattern {
      case Right(actual) =>
        actual ==== expected
    }

  }

}
