package parser.action

import org.jsoup.nodes.{Document, Element}

class SearchActionChain(actions: Seq[SearchAction]) {

  def execute()(implicit document: Document): Seq[Element] = {
    if (actions.isEmpty) Seq()
    else {
      val elements = actions.head.find()
      actions.tail.foldLeft(elements)((sequence, action) => action.filter(sequence))
    }
  }
}
