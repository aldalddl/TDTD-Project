# 토닥토닥 프로젝트 (Android Ver.)

<img src="https://img.shields.io/badge/platform-firebase-blue">  <img src="https://img.shields.io/badge/platform-android-green"> 

## Summary

![final](https://user-images.githubusercontent.com/63048392/115654781-cf9e8500-a36c-11eb-96b6-550dc4d99f2e.png)

이 안드로이드 어플리케이션은 불안 장애를 막고 집단 지성을 통해 치유하기 위해 개발된 어플입니다. 
안드로이드와 웹의 유기적인 연결을 통해서 자신의 불안 정도를 확인하고 해결 방안에 대해 제시할 수 있는 커뮤니티입니다.
얼굴 감정 인식, 챗봇 상담을 통해서 자신의 감정 정도를 파악하고 컬러테라피, 집단지성을 통해서 힘든 감정을 치유할 수 있습니다. 






## Requirements
- Android API level 15+
- Firebase Key
- Microsoft Azure API
- Adruino

## Description
- category menu

   4가지 분류(가족, 성, 대인관계, 자기개념)를 바탕으로 각각의 카테고리에 대한 커뮤니티 관찰 가능
   
- writing solution

  웹 챗봇을 통해 상담 진행 후에 가능
  
  웹 챗봇을 통해 상담을 진행하고 나면 그 정보다 database에 들어가게 되고 report menu 에서 그 정보를 확인 가능하다
  
  원하는 리스트 항목을 클릭하면 해당 상담에 대해 자신의 생각을 작성 가능

- report detail

  자신이 solution을 작성했다면 community에 올라간 것을 확인 가능
  
  좋아요 버튼 클릭 가능
  
  최신순, 인기순 버튼을 통해서 재정렬 가능
  
  해당 community 항목 클릭 시 자신의 상담 정보 열람 가능

- Face Recognition

  microsoft의 azure api 사용
  
  자신의 얼굴 사진을 찍고 감정에 대해서 분석
  
  분석 결과를 통해 감정과 퍼센트를 나타냄

## Demo App


<img src="https://user-images.githubusercontent.com/63048392/114255991-121da480-99f2-11eb-84ca-5a4dae51d699.png" width="200" height="350"> <img src="https://user-images.githubusercontent.com/63048392/114255996-13e76800-99f2-11eb-9704-c531e1c0a39e.png" width="200" height="350"> <img src="https://user-images.githubusercontent.com/63048392/114256087-a7209d80-99f2-11eb-9079-54ad1547d308.png" width="200" height="350"> <img src="https://user-images.githubusercontent.com/63048392/114256080-a25be980-99f2-11eb-805f-dfad0be6b6ff.png" width="200" height="350"> 


자세한 페이지에 대해서는 아래 링크를 통해 확인 가능

https://www.youtube.com/watch?v=prTo8yogprE&t=519s


## Firebase Query

- anxiety information: about_anxiety, cause1, cause2, result_color, result_explain, uid
- anxiety information for report: about_anxiety, cause1, cause2, result_color, result_explain, uid
- community: Cause1, date, recommendCount, uid, writing
- face recognition: myuid, percent, sentiment
- recommend: (destinationuid: true)

### iotFragment.java
- 아래 코드에 자신의 uuid와 blue tooth의 MAC주소를 기입합니다
```c
  private static final UUID MY_UUID = UUID.fromString(""); // SPP UUID service
  private static String address = ""; // MAC-address of Bluetooth module (you must edit this line)
```

- 아두이노 bluetooth 모듈을 켜서 아래 코드에서 연결 상태를 확인합니다
```c
 private void checkBTState() {
        // Check for Bluetooth support and then check to make sure it is turned on
        // Emulator doesn't support Bluetooth and will return null
        if(btAdapter==null) {
            errorExit("Fatal Error", "Bluetooth not support");
        } else {
            if (btAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
```
- 아래 코드를 통해서 블루투스와 통신을 하여 색을 바꿔줍니다.
- 버튼 클릭시 이벤트가 발생해서 대상의 색을 지정해주는 역할을 합니다.

```c
       h = new Handler() {
            public void handleMessage(android.os.Message msg) {
                switch (msg.what) {
                    case RECIEVE_MESSAGE:
                        byte[] readBuf = (byte[]) msg.obj;
                        String strIncom = new String(readBuf, 0, msg.arg1);
                        sb.append(strIncom);
                        int endOfLineIndex = sb.indexOf("\r\n");
                        if (endOfLineIndex > 0) {
                            String sbprint = sb.substring(0, endOfLineIndex);
                            sb.delete(0, sb.length());
                            txtArduino.setText("Data from Arduino: " + sbprint);
                            if(flag%4==3){
                                rlayout.setBackgroundColor(Color.rgb(255, 255, 255));
                            }
                            else if(flag%4==1){ //R
                                rlayout.setBackgroundColor(Color.rgb(255, 0, 0));
                            }
```


