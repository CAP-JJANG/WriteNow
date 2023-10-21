## 🙌 소개
**[ENG]**  
It is a wearable application using CSD-Model.

When using a small wearable device, have you ever experienced any inconvenience with a small keyboard panel?  
Write Now wanted to make the table a single panel to address this inconvenience.  
The written sound from the table is sent to the server every 1.5 seconds in the form of an object.  
The server uses the CSD-Model to derive predictions and returns them to the client.  

This allows you to enter letters into the device through a wide table instead of a small screen.

<br>

**[KOR]**  
CSD-Model을 활용한 웨어러블 애플리케이션입니다.

소형 웨어러블 기기 사용 시, 작은 키보드 패널로 인해 입력에 불편함을 느낀 적이 있으신가요?  
Write Now는 이러한 불편을 해소하기 위해 테이블을 하나의 패널로 만들고자 하였습니다.  
테이블에서 글씨 쓰는 음향은 객체 형태로 1.5초마다 서버로 전송됩니다.  
서버에서 해당 객체를 CSD-Model을 활용하여 예측값을 도출하고, 클라이언트로 반환됩니다.

이로써 작은 화면 대신, 넓은 테이블을 통해 기기에 글자를 입력할 수 있습니다.

<br><br>
## 💪 주요 기능
**[ENG]**  
1. You can make sound-based input.
2. You can copy recognized text and use it like a keyboard.
3. You can send recognized text to a message.
4. If you type 'sos', you can call an emergency contact by pressing the OK button.
The default emergency contact is 112, and you can set up an emergency contact via 'SETTING SOS' on the start screen.

<br>

**[KOR]**  
1. 음향 기반 입력을 할 수 있습니다.
2. 인식된 텍스트를 복사하여 키보드처럼 사용할 수 있습니다.
3. 인식된 텍스트를 메시지로 보낼 수 있습니다.
4. 'sos'라고 입력했을 경우, 확인 버튼을 누르면 긴급 연락처로 전화할 수 있습니다.
   기본 긴급 연락처는 112이며, 시작 화면에서 'SETTING SOS'를 통해 긴급 연락처를 설정할 수 있습니다.

<br><br>
## 🦾 주요 기술
**Mobile - Android**
* Android Studio: Giraffe | 2022.3.1
* Gradle plugin: 8.1.1
* JDK: jbr-17
* Min SDK: 30
* Retrofit: 2.9.0
* Livedata: 2.6.2


<br><br>
## 🔗 서비스 아키텍처
![WriteNow 프로젝트 구조](https://github.com/CAP-JJANG/WriteNow/assets/92065911/386b8fec-048d-4fe9-b0f5-2787ab2a2edf)


<br><br>
## 🔗 디렉터리 구조
```
writenow
├── MyApplication.kt
├── MySharedPreferences.kt
├── SplashActivity.kt
├── api
│   └── RecordService.kt
├── apiManager
│   └── RecordApiManager.kt
├── base
│   ├── BaseDialogFragment.kt
│   └── BaseFragment.kt
├── model
│   ├── GetTestModel.kt
│   ├── RecordModel.kt
│   └── ResultModel.kt
└── ui
    ├── ActionCmdDialog.kt
    ├── ActionTextDialog.kt
    ├── MainActivity.kt
    ├── RecordFragment.kt
    └── ShowResultFragment.kt
```

<br><br>
## 👟 실행 방법
**[ENG]**  
[Install apk files](https://github.com/CAP-JJANG/WriteNow/blob/37bfebba105667258906238c51cd72c422ac3fe0/WriteNow.apk) and run on Android 11.0 Wear OS 3 and later devices  

<br>

**[KOR]**  
Android 11.0 Wear OS 3 이상 기기에 [apk 파일 설치](https://github.com/CAP-JJANG/WriteNow/blob/37bfebba105667258906238c51cd72c422ac3fe0/WriteNow.apk) 후 실행

<br><br>
## 👀 실행 화면
* 음향 기반 입력을 하는 모습  
  <img width="315" alt="image" src="https://github.com/CAP-JJANG/WriteNow/assets/92065911/d2af1bad-82d4-43e0-9497-7496baaf4408">
  
* 인식된 텍스트를 복사하여 키보드처럼 사용 가능  
  <img width="312" alt="image" src="https://github.com/CAP-JJANG/WriteNow/assets/92065911/b974ee8e-f866-4647-b940-a31392c41693">

* 인식된 텍스트를 메시지로 전송 가능  
   <img width="322" alt="image" src="https://github.com/CAP-JJANG/WriteNow/assets/92065911/5979d44a-d6db-4965-bf33-a0a9106f72f6">

* SETTING SOS를 통해 긴급 연락처 설정  
   <img width="313" alt="image" src="https://github.com/CAP-JJANG/WriteNow/assets/92065911/fc14ab26-30de-4faf-83eb-9fae170ef072">
  
* 'sos'라고 입력했을 경우, 확인 버튼을 누르면 긴급 연락처로 전화 가능  
  <img width="313" alt="image" src="https://github.com/CAP-JJANG/WriteNow/assets/92065911/0043c93e-0bed-48c9-8052-d528fd44a424">


<br>
<img width="906" alt="image" src="https://github.com/CAP-JJANG/WriteNow/assets/92065911/e32f3f42-2cd6-4da4-8b82-fbf71987a0c1">


<br><br>
## 🤖 라이센스
This project is licensed under the Apache License 2.0 - see the [LICENSE](https://github.com/CAP-JJANG/WriteNow/blob/main/LICENSE) file for details.  
[OSS Notice](https://github.com/CAP-JJANG/WriteNow/blob/main/OSS-Notice.md) sets forth attribution notices for third party software that may be contained in this application.
