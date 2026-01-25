package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanSprayJsonInstances.MyBox
import spray.json.*

/** @author Kevin Lee
  * @since 2025-08-10
  */
object JsonWriterWithSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    property("test spray-json JsonWriter", testJsonWriter)
  )

  def testJsonWriter: Property = for {
    id       <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name     <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive <- Gen.boolean.log("isActive")
    myBox    <- Gen.constant(MyBox(id, name, isActive)).log("myBox")
  } yield {
    val expected =
      JsObject(
        "id"       -> JsNumber(id),
        "name"     -> JsString(name),
        "isActive" -> JsBoolean(isActive),
      )

    val actual = myBox.toJson

    Result.all(
      List(
        (actual ==== expected).log("JSON comparison failed"),
        (actual.compactPrint ==== expected.compactPrint).log("JSON String comparison failed"),
      )
    )
  }

}
