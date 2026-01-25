package orphan_test

import hedgehog.*
import hedgehog.runner.*
import orphan_instance.OrphanSprayJsonInstances.MyBox
import spray.json.*

/** @author Kevin Lee
  * @since 2026-01-25
  */
object JsonReaderWithSprayJsonSpec extends Properties {

  override def tests: List[Test] = List(
    property("test spray-json JsonReader", testJsonReader)
  )

  def testJsonReader: Property = for {
    id       <- Gen.int(Range.linear(0, Int.MaxValue)).log("id")
    name     <- Gen.string(Gen.alpha, Range.linear(0, 10)).log("name")
    isActive <- Gen.boolean.log("isActive")
    myBox    <- Gen.constant(MyBox(id, name, isActive)).log("myBox")
  } yield {
    val json       = JsObject(
      "id"       -> JsNumber(id),
      "name"     -> JsString(name),
      "isActive" -> JsBoolean(isActive),
    )
    val jsonString = json.compactPrint

    val expected = myBox
    val actual   = jsonString.parseJson.convertTo[MyBox]

    actual ==== expected

  }

}
