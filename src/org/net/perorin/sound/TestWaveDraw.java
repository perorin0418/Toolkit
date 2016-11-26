package org.net.perorin.sound;


import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
* 音声(wav)データの波形を見るプログラム
* ただし、Wav(PCM・リトルエディアン)形式で保存された
* ファイルのチャンネル１のみ出力
*
* @author karura
*
*/
public class TestWaveDraw extends Application
{
   // 定数
   private final String    fileName    = "C:/Users/perorin/Desktop/test.wav";        // チャートに表示する音声ファイルへのパス
   private final double    sec         = 0.5;                     // チャートに表示する期間(s)

   // 取得する音声情報用の変数
   private AudioFormat     format  = null;
   private int[]           values  = null;

   public static void main(String[] args) {
       launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception
   {
       // フォント色がおかしくなることへの対処
       System.setProperty( "prism.lcdtext" , "false" );

       // シーングラフの作成
       HBox        root    = new HBox();

       // チャートを作成
       init();
       root.getChildren().add( createLineChart() );            // 折れ線グラフの追加

       // シーンの作成
       Scene       scene   = new Scene( root , 900 , 300 );

       // ウィンドウ表示
       primaryStage.setScene( scene );
       primaryStage.show();

   }

   /**
    * 音声ファイルを読み込み、メタ情報とサンプリング・データを取得
    * @throws IOException
    * @throws UnsupportedAudioFileException
    */
   public void init() throws Exception
   {
       // 音声ストリームを取得
       File                file    = new File( fileName );
       AudioInputStream    is      = AudioSystem.getAudioInputStream( file );

       // メタ情報の取得
       format = is.getFormat();
       System.out.println( format.toString() );

       // 取得する標本数を計算
       // 1秒間で取得した標本数がサンプルレートであることから計算
       int mount   = (int) ( format.getSampleRate() * sec );

       // 音声データの取得
       values      = new int[ mount ];
       for( int i=0 ; i<mount ; i++ )
       {
           // 1標本分の値を取得
           int     size        = format.getFrameSize();
           byte[]  data        = new byte[ size ];
           int     readedSize  = is.read(data);

           // データ終了でループを抜ける
           if( readedSize == -1 ){ break; }

           // 1標本分の値を取得
           switch( format.getSampleSizeInBits() )
           {
               case 8:
                   values[i]   = (int) data[0];
                   break;
               case 16:
                   values[i]   = (int) ByteBuffer.wrap( data ).order( ByteOrder.LITTLE_ENDIAN ).getShort();
                   break;
               default:
           }
       }

       // 音声ストリームを閉じる
       is.close();
   }

   /**
    * 折れ線グラフで波形表示
    * @return
    */
   @SuppressWarnings("unchecked")
   public Node createLineChart()
   {
       // 折れ線グラフ
       NumberAxis                  xAxis   = new NumberAxis();
       NumberAxis                  yAxis   = new NumberAxis();
       LineChart<Number, Number>   chart   = new LineChart<Number, Number>( xAxis , yAxis );
       chart.setMinWidth( 900 );

       // データを作成
       Series< Number , Number > series1    = new Series<Number, Number>();
       series1.setName( "チャンネル１"  );
       for( int i=0 ; i<values.length ; i++ )
       {
           series1.getData().add( new XYChart.Data<Number, Number>( i , values[i] ) );
       }

       // データを登録
       chart.getData().addAll( series1 );

       // タイトルを設定
       String  title   = String.format( "『%s』の音声波形データ（サンプリング周波数：%fHz）" , fileName , format.getSampleRate() );
       chart.setTitle( title );

       // 見た目を調整
       chart.setCreateSymbols(false);                                                      // シンボルを消去
       series1.getNode().lookup(".chart-series-line").setStyle("-fx-stroke-width: 1px;");  // 線を細く

       return chart;
   }
}