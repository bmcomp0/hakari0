# 重量計記録用Androidアプリ
EJ-200Bで記録した値をesp32のBLEで送信。値を受け取るAndroidアプリ。  

EJ-200Bの設定： オートプリントBモード(計測結果が安定した重さ出力し、-0.04~+0.04gまで値が下がりム一度のせると再計測開始)  
## システム概要
<kbd><img src="/images_readme/system_overview.png"></kbd>  


## BLE
### 接続フロー
![](/images_readme/BLE_Flow.png)  
ハッシュ値を交換することで，認証を行う。認証が通るとesp32からnotifyで値が送られてくる。

## 構成
```
.
├─model
│   └───SampleViewModel.kt
│
└─scalesseparatefileble
    ├─MainActivity.kt
    │
    ├─bluetooth
    │   └─BluetoothManager.kt
    │
    ├─data
    │   └─SaveCsvFileWriter.kt
    │
    ├─ui
    │   ├─BLEData.kt
    │   ├─BLEDataList.kt
    │   ├─BLEMain.kt
    │   ├─CSVDataShow.kt
    │   ├─CSVFileList.kt
    │   └─DataLabel.kt
    │
    └─util
        BluetoothUtilities.kt
        ColumnItem.kt
        └─CustomScipeToDismiss.kt
```

## アプリ画面と動作
### First Screen
BLEData.ktに記述。  
`Scan`を押すことで，近くにあるBLEデバイスを検出し，表示する。  
`Device connect`でMyBLEDeviceに接続する。  
TopBarの矢印でも次の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172043.png" width="320px"></kbd>

### Second Screen
DataLabel.ktに記述。  
データのラベルを入力する。  
TopBarの矢印でも前後の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172057.png" width="320px"></kbd>

### Third Screen
BLEData.ktに記述。  
`追加`でesp32から送られてきた値をリストに追加する。  
リストにある値をスワイプすることで削除できる。  
`元に戻す`で削除した値を復元  
`保存`でアプリ固有の外部ストレージにcsvファイルで保存する。  
TopBarの矢印でも前後の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172150.png" width="320px"></kbd>

### Fourth Screen
CSVFileList.ktに記述。  
アプリ固有の外部ストレージにあるファイルを表示する。  
ファイル名をタップすることでファイルの中身を表示する。  
TopBarの矢印で前の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172204.png" width="320px"></kbd>

### Fifth Screen
CSVDataShow.ktに記述。  
ファイルの中身を表示する。  
<kbd><img src="/images_readme/Screenshot_20240201-172217.png" width="320px"></kbd>
