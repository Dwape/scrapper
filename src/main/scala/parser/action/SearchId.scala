package parser.action
import org.jsoup.nodes.{Document, Element}

case class SearchId(id: String) extends SearchAction {

  override def find()(implicit document: Document): Seq[Element] = {
    val result = document.getElementById(id)
    if (result != null) Seq(result)
    else Seq()
  }

  override def filter(elements: Seq[Element])(implicit document: Document): Seq[Element] = {
    elements.filter(element => element.id() == id)
    // This method should never be called.
  }
}
