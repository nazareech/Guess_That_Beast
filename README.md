# Guess That Beast

## 🇺🇸 English

**"Guess That Beast"** is an exciting game that challenges players to test their knowledge of memes and characters. Progress through levels by answering correctly, earn points, unlock new stages, and enjoy the variety.

---

### 🎮 Game Features:
- **Multiple Levels:** Unlock new levels by reaching a correct answer threshold.
- **Points & Lives System:** Players have a limited number of lives to manage wisely.
- **Shop:** Use earned points to buy additional lives.
- **Save System:** Game progress is automatically saved in `gamestate.json`.
- **Data Protection:** Game data is encrypted for security.

---

### 🛠️ Project Setup:

#### Requirements:
- **Java SDK** 23  
- **Maven**  
- **JavaFX** 23.0.1  

#### Third-Party Libraries:
1. [`javafx-controls`](https://mvnrepository.com/artifact/org.openjfx/javafx-controls)
2. [`javafx-fxml`](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml)
3. [`ControlsFX`](https://mvnrepository.com/artifact/org.controlsfx/controlsfx)
4. [`BootstrapFX`](https://mvnrepository.com/artifact/org.kordamp.bootstrapfx/bootstrapfx-core)
5. [`Jackson Databind`](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)

#### Build Instructions:
1. Make sure all dependencies are listed in `pom.xml`.
2. Install dependencies using Maven:

   ```bash
   mvn clean install
   ```
3. Run OpenApp.java to start the game.

### File Setup:
- **Resources:** All assets (images, JSON files) are in src/main/resources.
- **Settings File:** Game progress is saved in an encrypted gamestate.json.

---

## 🚀 How to Play:
  - **Launch the game:** Run OpenApp.java.
  - **Choose a level:** Pick an available level to start.
  - **Answer questions:** Select the correct meme or character.
  - **Earn points:** Spend them in the shop for lives.
  - **Unlock levels:** Reach 50% accuracy to unlock the next stage.

---

## 📥 Download
You can download the Windows version here:
➡️ [Guess That Beast - Windows (.exe)](https://github.com/nazareech/Guess_That_Beast/releases/latest)

*Note:* All resources must be available in the resources folder for progress saving.

---

## 🔐 Security Features:
  - Progress (points, lives, unlocked levels) is saved in encrypted gamestate.json.
  - A fixed-key encryption/decryption algorithm is used locally.

---

## 💡 Future Plans:
  - Expand shop with new features (e.g. hints).
  - Add more levels.
  - Enhance visuals with effects and animations.

---

👥 Author:
Nazar Protasov

---
---

## ua Українська

**"Guess That Beast"** — це захоплююча гра, яка дозволяє гравцям тестувати свої знання про меми та персонажів. Прогресуйте через рівні, відповідаючи правильно на питання, збираючи бали за правильні відповіді, відкриваючи нові рівні та насолоджуйтесь різноманітністю.

---

## 🎮 Функціонал гри:
- **Кілька рівнів:** Гравці можуть поступово розблоковувати нові рівні, якщо досягнуть порогу правильних відповідей.
- **Баланс балів та життів:** Гравці мають обмежену кількість життів, які потрібно обережно використовувати.
- **Магазин:** Використовуйте зароблені бали для купівлі додаткових життів.
- **Система збереження:** Ігровий прогрес автоматично зберігається у файл `gamestate.json`.
- **Захист даних:** Використовується шифрування для збереження ігрових даних.

---

## 🛠️ Інструкція з налаштування проєкту:

### Вимоги:
- **Java SDK** 23
- **Maven**
- **JavaFX** 23.0.1

### Сторонні бібліотеки:
1. [`javafx-controls`](https://mvnrepository.com/artifact/org.openjfx/javafx-controls)
2. [`javafx-fxml`](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml)
3. [`ControlsFX`](https://mvnrepository.com/artifact/org.controlsfx/controlsfx)
4. [`BootstrapFX`](https://mvnrepository.com/artifact/org.kordamp.bootstrapfx/bootstrapfx-core)
5. [`Jackson Databind`](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)

### Збірка проєкту:
1. Впевніться, що в `pom.xml` вказані необхідні залежності.
2. Завантажте/встановіть необхідні залежності за допомогою Maven командою:
   
   ```bash
   mvn clean install
   ```
4. Запустіть файл `OpenApp.java`, щоб стартувати гру.

### Налаштування файлів:
- **Ресурси:**
  Усі ресурси (зображення, JSON тощо) розташовані у папці `src/main/resources`.
- **Файл налаштувань:**
  Ігровий прогрес зберігається у файлі `gamestate.json` у захищеному форматі (шифрується).

---

## 🚀 Як грати:
1. **Розпочніть гру:**
   Запустіть програму, клацнувши на файл `OpenApp.java`.
2. **Виберіть рівень:**
   Обирайте доступний рівень і починайте.
3. **Відповідайте на запитання:**
   Вибирайте одну із запропонованих відповідей для мемів.
4. **Зароблені бали:**
   Використовуйте зароблені бали у магазині для купівлі життів.
5. **Прогрес у рівнях:**
   Досягніть 50% правильних відповідей, щоб розблокувати наступний рівень.

---

## 📦 Завантаження

Ви можете завантажити гру для Windows за посиланням:
➡️ [Guess That Beast - Windows (.exe)](https://github.com/nazareech/Guess_That_Beast/releases/latest)

*Примітка:* Для збереження прогресу всі ресурси повинні бути доступні в папці `resources`.

---

## 🔐 Особливості захисту:
- Ігровий прогрес (бали, життя, розблоковані рівні) зберігається у зашифрованому JSON-файлі (`gamestate.json`).
- Використовується простий алгоритм шифрування/дешифрування з фіксованим ключем, що зберігається локально.

---

## 💡 Ідеї для майбутнього розвитку:
- **Розширення магазину з новими функціями (наприклад, купівля підказок).**
- **Розробка додаткових рівнів.**
- **Додати ефекти та анімації на екрані користувача.**

---

## 👥 Автор:
- Nazar Protasov
