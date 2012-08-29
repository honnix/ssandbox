package gd

import com.tinkerpop.blueprints.impls.neo4j.Neo4jGraph

object GraphDemo extends App {
  val graph = new Neo4jGraph("my_graph")
  val a = graph.addVertex(null)
  val b = graph.addVertex(null)
  a.setProperty("name", "marko")
  a.setProperty("name", "peter")
  val e = graph.addEdge(null, a, b, "knows")
  e.setProperty("since", 2006)
  graph.shutdown
}
