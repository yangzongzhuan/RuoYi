import random
import time

class Plant:
    def __init__(self, name, health, attack_power, cost):
        self.name = name
        self.health = health
        self.attack_power = attack_power
        self.cost = cost

    def is_alive(self):
        return self.health > 0

    def attack(self, zombie):
        zombie.health -= self.attack_power
        print(f"{self.name} 攻击 {zombie.name}，造成 {self.attack_power} 点伤害!")

class Peashooter(Plant):
    def __init__(self):
        super().__init__(name="豌豆射手", health=10, attack_power=5, cost=50)

class Sunflower(Plant):
    def __init__(self):
        super().__init__(name="向日葵", health=5, attack_power=0, cost=50)

    def generate_sun(self):
        sun = random.randint(20, 50)
        print(f"向日葵生成了 {sun} 阳光点数!")
        return sun

class Zombie:
    def __init__(self, name, health, attack_power):
        self.name = name
        self.health = health
        self.attack_power = attack_power

    def is_alive(self):
        return self.health > 0

    def attack(self, plant):
        plant.health -= self.attack_power
        print(f"{self.name} 攻击 {plant.name}，造成 {self.attack_power} 点伤害!")

class BasicZombie(Zombie):
    def __init__(self):
        super().__init__(name="普通僵尸", health=20, attack_power=3)

class Game:
    def __init__(self):
        self.sun_points = 100
        self.plants = []
        self.zombies = [BasicZombie() for _ in range(3)]

    def plant_turn(self):
        print("\n--- 植物回合 ---")
        print(f"当前阳光点数: {self.sun_points}")
        print("1: 种植豌豆射手 (50 阳光)")
        print("2: 种植向日葵 (50 阳光)")
        print("3: 跳过回合")
        choice = input("选择一个动作: ")

        if choice == "1" and self.sun_points >= 50:
            self.plants.append(Peashooter())
            self.sun_points -= 50
            print("你种植了一个豌豆射手!")
        elif choice == "2" and self.sun_points >= 50:
            sunflower = Sunflower()
            self.plants.append(sunflower)
            self.sun_points -= 50
            self.sun_points += sunflower.generate_sun()
            print("你种植了一个向日葵!")
        elif choice == "3":
            print("你跳过了这回合。")
        else:
            print("无效选择或阳光点数不足。")

    def zombie_turn(self):
        print("\n--- 僵尸回合 ---")
        for zombie in self.zombies:
            if zombie.is_alive():
                if self.plants:
                    target = random.choice(self.plants)
                    zombie.attack(target)
                    if not target.is_alive():
                        print(f"{target.name} 被摧毁了!")
                        self.plants.remove(target)
                else:
                    print("僵尸正在攻击你的花园!")

    def plant_attacks(self):
        print("\n--- 植物攻击 ---")
        for plant in self.plants:
            if isinstance(plant, Peashooter):
                if self.zombies:
                    target = random.choice(self.zombies)
                    plant.attack(target)
                    if not target.is_alive():
                        print(f"{target.name} 被击败了!")
                        self.zombies.remove(target)

    def is_game_over(self):
        if not self.zombies:
            print("恭喜你！你击败了所有僵尸！")
            return True
        if not self.plants and any(z.is_alive() for z in self.zombies):
            print("僵尸入侵了你的花园。游戏结束！")
            return True
        return False

    def play(self):
        print("欢迎来到植物大战僵尸 (文本版)!")
        while True:
            self.plant_turn()
            self.plant_attacks()
            self.zombie_turn()
            if self.is_game_over():
                break
            time.sleep(2)

if __name__ == "__main__":
    game = Game()
    game.play()
