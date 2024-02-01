# 重量計記録用Androidアプリ
EJ-200Bで記録した値をesp32のBLEで送信。値を受け取るAndroidアプリ。
## システム概要
![](/images_readme/system_overview.png)  
ハッシュ値を交換することで，認証を行う。認証が通るとesp32からnotifyで値が送られてくる。

## BLE

## アプリ画面と動作
### First Screen
`Scan`を押すことで，近くにあるBLEデバイスを検出し，表示する。  
`Device connect`でMyBLEDeviceに接続する。  
TopBarの矢印でも次の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172043.png" width="320px"></kbd>

### Second Screen
データのラベルを入力する。  
TopBarの矢印でも前後の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172057.png" width="320px"></kbd>

### Third Screen
`追加`でesp32から送られてきた値をリストに追加する。  
リストにある値をスワイプすることで削除できる。  
`元に戻す`で削除した値を復元  
`保存`でアプリ固有の外部ストレージにcsvファイルで保存する。  
TopBarの矢印でも前後の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172150.png" width="320px"></kbd>

### Fourth Screen
アプリ固有の外部ストレージにあるファイルを表示する。  
ファイル名をタップすることでファイルの中身を表示する。  
TopBarの矢印で前の画面に遷移できる。  
<kbd><img src="/images_readme/Screenshot_20240201-172204.png" width="320px"></kbd>

### Fifth Screen
ファイルの中身を表示する。  
<kbd><img src="/images_readme/Screenshot_20240201-172217.png" width="320px"></kbd>
