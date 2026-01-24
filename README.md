# 🎮 Gray Screen Therapy (GST)
[ [English](#english) | [Türkçe](#turkce) ]

## English
Gray Screen Therapy is a lightweight Java-based automation tool designed for League of Legends players who are tired of staring at a gray screen for 40+ seconds. Using Riot's Live Client Data API, GST detects your death in real-time and automatically launches a "therapy session" (YouTube Shorts) to keep you distracted.

---

## Key Features

* **Automated Therapy:** Instantly opens a random YouTube Shorts session as soon as you die.
* **Wasted Time Tracker:** Tracks every second you spend in the gray screen. Use this data to reflect on your life choices.
* **Therapy Toggle:** An interactive UI button to pause/resume the automation whenever you need to focus back.
* **Standalone Installer:** No Java? No problem. Bundled with a mini JRE, so it works out of the box.
* **⚠️NOTE⚠️**: Automation only triggers if your respawn timer is longer than 10 seconds. Why? Because 10 seconds pass in a blink. By the time the browser launches and the video starts loading, you'd already be respawning.


## Installation & Usage

1.  **Download:** Go to the [Releases](https://github.com/SpaceLaikaa/GrayScreenTherapy/releases/) page and download `GrayScreenTherapy-1.0.exe`.
2.  **Install:** Run the installer and follow the instructions. A desktop shortcut will be created for you.
3.  **Run:** Launch the app, then start a League of Legends match.
4.  **Die:** Go ahead, face-check that bush. Your therapy will start automatically.


## Technical Details

* **Language:** Java
* **Libraries:** JNA (for Windows API control), GSON (for JSON parsing), Riot Live Client Data API.
* **Packaging:** Bundled with WiX Toolset & jpackage for a standalone Windows experience.



*Developed by Arda Akkas(SpaceLaikaa) - https://github.com/SpaceLaikaa - https://www.linkedin.com/in/arda-akkas-69913b336/*

---
<a name="turkce"></a>
## Türkçe
Gray Screen Therapy, League of Legends oynarken o 40 küsur saniyelik gri ekrana bakmaktan sıkılan oyuncular için tasarlanmış, Java tabanlı hafif bir otomasyon aracıdır. Riot'un Live Client Data API altyapısını kullanan GST, öldüğünüz anı gerçek zamanlı olarak saptar ve dikkatinizi dağıtmak için otomatik olarak bir "terapi seansı" (YouTube Shorts) başlatır.

---

## Özellikler
* **Otomatik Terapi:** Vadi'de kaderinize yenik düştüğünüz an, dikkatinizi toparlamanız (veya daha çok gülmeniz) için otomatik bir YouTube Shorts sekmesi açılır.
* **Boşa Geçen Zaman Sayacı:** Gri ekranda geçirdiğiniz her saniyeyi takip eder. Böylece boşa beklediğiniz zamanı görebilirsiniz.
* **Terapi Butonu:** Oyuna odaklanmanız gerektiğinde otomasyonu tek tıkla duraklatıp devam ettirebilirsiniz.
* **Bağımsız Kurulum:** Bilgisayarınızda Java yüklü olmasa bile çalışır.
* **⚠️NOT⚠️**: Uygulama yalnızca yeniden canlanma süreniz 10 saniyeden uzun olduğunda çalışır. Neden? Çünkü 10 saniyede tarayıcı açılıp video yüklenene kadar zaten çoktan canlanmış olursunuz.

### Kurulum ve Kullanım

1.  **İndir:** [Releases](https://github.com/SpaceLaikaa/GrayScreenTherapy/releases/) sayfasından hangi güncel sürümdeyse uygulama o sürümün .exe dosyasını indirin.
2.  **Kur:** Yükleyiciyi çalıştırın ve talimatları izleyin. Masaüstünüze otomatik bir kısayol oluşturulacaktır.
3.  **Çalıştır:** Uygulamayı açın, ardından bir League of Legends maçı başlatın.
4.  **Öl:** Öldüğünüzde terapiniz otomatik olarak başlayacaktır.

### Teknik Detaylar

* **Dil:** Java
* **Kütüphaneler:** JNA (Windows pencere kontrolü için), GSON (JSON verileri için), Riot Live Client Data API.
* **Paketleme:** Kusursuz bir Windows deneyimi için WiX Toolset ve jpackage ile paketlenmiştir.


*Geliştirici: Arda Akkaş (SpaceLaikaa) - https://github.com/SpaceLaikaa - https://www.linkedin.com/in/arda-akkas-69913b336/*
