class Food

abstract class Animal {
  type T <: Food

  def eat(food: T)
}

class Grass extends Food

class Cow extends Animal {
  type T = Grass

  override def eat(food: Grass) {}
}
