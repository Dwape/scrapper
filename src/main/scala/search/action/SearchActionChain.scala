package search.action

import org.jsoup.nodes.{Document, Element}

/**
 * A chain of search actions that are executed one after the other.
 * The input for an action is the output of the previous one.
 * @param actions The actions that will be executed, in order.
 */
class SearchActionChain(actions: Seq[SearchAction]) {

  /**
   * Executes all the actions in the chain, one after the other.
   * For the first action, the find method is called, while the filter method is called for all other actions.
   * @param document The document that will be searched.
   * @return A Sequence with all the elements that match the search criteria.
   */
  def execute()(implicit document: Document): Seq[Element] = {
    if (actions.isEmpty) Seq()
    else {
      val elements = actions.head.find()
      actions.tail.foldLeft(elements)((sequence, action) => action.filter(sequence))
    }
  }
}
