package com.example.andrd_ado_vdo_tkbk_demo.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Environment;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

import com.example.andrd_ado_vdo_tkbk_demo.R;
import com.example.andrd_ado_vdo_tkbk_demo.voip.AndrdAdoVdoTkbkStng;
import com.example.andrd_ado_vdo_tkbk_demo.voip.MyMediaPocsThrd;
import HeavenTao.Media.*;
//主界面。
public class ConfigVoIPActivity extends AppCompatActivity implements View.OnTouchListener
{
    public String m_CurClsNameStrPt = this.getClass().getSimpleName(); //存放当前类名称字符串。

    public View m_MainLyotViewPt; //存放主布局视图的指针。
    public View m_XfrPrtclStngLyotViewPt; //存放传输协议设置布局视图的指针。
    public View m_StngLyotViewPt; //存放设置布局视图的指针。
    public View m_AjbStngLyotViewPt; //存放音频自适应抖动缓冲器设置布局视图的指针。
    public View m_SaveStsToTxtFileStngLyotViewPt; //存放保存状态到Txt文件设置布局视图的指针。
    public View m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt; //存放保存音视频输入输出到Avi文件设置布局视图的指针。
    public View m_SpeexAecStngLyotViewPt; //存放Speex声学回音消除器设置布局视图的指针。
    public View m_WebRtcAecmStngLyotViewPt; //存放WebRtc定点版声学回音消除器设置布局视图的指针。
    public View m_WebRtcAecStngLyotViewPt; //存放WebRtc浮点版声学回音消除器设置布局视图的指针。
    public View m_SpeexWebRtcAecStngLyotViewPt; //存放SpeexWebRtc三重声学回音消除器设置布局视图的指针。
    public View m_SpeexPrpocsNsStngLyotViewPt; //存放Speex预处理器的噪音抑制设置布局视图的指针。
    public View m_WebRtcNsxStngLyotViewPt; //存放WebRtc定点版噪音抑制器设置布局视图的指针。
    public View m_WebRtcNsStngLyotViewPt; //存放WebRtc浮点版噪音抑制器设置布局视图的指针。
    public View m_SpeexPrpocsStngLyotViewPt; //存放Speex预处理器设置布局视图的指针。
    public View m_SpeexCodecStngLyotViewPt; //存放Speex编解码器设置布局视图的指针。
    public View m_SaveAdoInptOtptToWaveFileStngLyotViewPt; //存放保存音频输入输出到Wave文件设置布局视图的指针。
    public View m_OpenH264CodecStngLyotViewPt; //存放OpenH264编解码器设置布局视图的指针。
    public View m_SystemH264CodecStngLyotViewPt; //存放系统自带H264编解码器设置布局视图的指针。

    public View m_CurActivityLyotViewPt; //存放当前界面布局视图的指针。
    public MyMediaPocsThrd m_MyMediaPocsThrdPt; //存放媒体处理线程的指针。
    public ConfigActivityHandler m_MainActivityHandlerPt; //存放主界面消息处理的指针。
    public int m_FirstIPaddrItemIsSelected = 0; //存放第一次IP地址是否已选择，防止在第一次读取设置后IP地址被重置。

    public String m_ExternalDirFullAbsPathStrPt; //存放扩展目录完整绝对路径字符串的指针。

    public RelativeLayout spam_call_layout;   // 更多设置

    public LinearLayout LogLinearLyotId, LogLinear_title; //显示日志
    public Button show_log_btn;

    public boolean isShowLog;

    //Activity创建消息。
    @Override protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        Log.i( m_CurClsNameStrPt, "onCreate" );
        isShowLog = true;
        //创建布局。
        {
            LayoutInflater p_LyotInflater = LayoutInflater.from( this );

            m_MainLyotViewPt = p_LyotInflater.inflate( R.layout.fragment_config_voip, null );

            m_XfrPrtclStngLyotViewPt = p_LyotInflater.inflate( R.layout.xfr_prtcl_stng_lyot, null );
            m_StngLyotViewPt = p_LyotInflater.inflate( R.layout.stng_lyot, null );

            m_AjbStngLyotViewPt = p_LyotInflater.inflate( R.layout.ajb_stng_lyot, null );
            m_SaveStsToTxtFileStngLyotViewPt = p_LyotInflater.inflate( R.layout.save_sts_to_txt_file_stng_lyot, null );
            m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt = p_LyotInflater.inflate( R.layout.save_ado_vdo_inpt_otpt_to_avi_file_stng_lyot, null );
            m_SpeexAecStngLyotViewPt = p_LyotInflater.inflate( R.layout.speex_aec_stng_lyot, null );
            m_WebRtcAecmStngLyotViewPt = p_LyotInflater.inflate( R.layout.webrtc_aecm_stng_lyot, null );
            m_WebRtcAecStngLyotViewPt = p_LyotInflater.inflate( R.layout.webrtc_aec_stng_lyot, null );
            m_SpeexWebRtcAecStngLyotViewPt = p_LyotInflater.inflate( R.layout.speex_webrtc_aec_stng_lyot, null );
            m_SpeexPrpocsNsStngLyotViewPt = p_LyotInflater.inflate( R.layout.speex_prpocs_ns_stng_lyot, null );
            m_WebRtcNsxStngLyotViewPt = p_LyotInflater.inflate( R.layout.webrtc_nsx_stng_lyot, null );
            m_WebRtcNsStngLyotViewPt = p_LyotInflater.inflate( R.layout.webrtc_ns_stng_lyot, null );
            m_SpeexPrpocsStngLyotViewPt = p_LyotInflater.inflate( R.layout.speex_prpocs_stng_lyot, null );
            m_SpeexCodecStngLyotViewPt = p_LyotInflater.inflate( R.layout.speex_codec_stng_lyot, null );
            m_SaveAdoInptOtptToWaveFileStngLyotViewPt = p_LyotInflater.inflate( R.layout.save_ado_inpt_otpt_to_wave_file_stng_lyot, null );
            m_OpenH264CodecStngLyotViewPt = p_LyotInflater.inflate( R.layout.openh264_codec_stng_lyot, null );
            m_SystemH264CodecStngLyotViewPt = p_LyotInflater.inflate( R.layout.systemh264_codec_stng_lyot, null );
        }

        //显示布局。
        setContentView( m_MainLyotViewPt ); //设置主界面的内容为主布局。
        m_CurActivityLyotViewPt = m_MainLyotViewPt; //设置当前界面布局视图。
        ( ( Button )findViewById( R.id.PttBtnId ) ).setOnTouchListener( this ); //设置一键即按即通按钮的触摸监听器。
        LogLinearLyotId = findViewById(R.id.LogLinearLyotId);   //log信息
        LogLinear_title = findViewById(R.id.LogLinear_title);   //log标题和清空日志按钮
        show_log_btn = findViewById(R.id.show_log_btn);         //显示按钮
        show_log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShowLog == true) {
                    LogLinear_title.setVisibility(View.VISIBLE);
                    LogLinearLyotId.setVisibility(View.VISIBLE);
                    show_log_btn.setText("隐藏日志");
                    isShowLog = false;
                }else{
                    LogLinear_title.setVisibility(View.GONE);
                    LogLinearLyotId.setVisibility(View.GONE);
                    show_log_btn.setText("显示日志");
                    isShowLog = true;
                }
            }
        });
        //请求权限。
        MediaPocsThrd.RqstPrmsn( this, 1, 1, 1, 1, 0, 1, 1, 1, 1 );

        //初始化消息处理。
        m_MainActivityHandlerPt = new ConfigActivityHandler();
        m_MainActivityHandlerPt.m_MainActivityPt = this;

        //设置AppID文本框。
        //( ( TextView ) m_MainLyotViewPt.findViewById( R.id.AppIDTxtId ) ).setText( "AppID：" + getApplicationContext().getPackageName() );

        //设置扩展目录完整绝对路径字符串。
        {
            if( getExternalFilesDir( null ) != null )
            {
                m_ExternalDirFullAbsPathStrPt = getExternalFilesDir( null ).getPath();
            }
            else
            {
                m_ExternalDirFullAbsPathStrPt = Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + getApplicationContext().getPackageName();
            }

            String p_InfoStrPt = "扩展目录完整绝对路径：" + m_ExternalDirFullAbsPathStrPt;
            Log.i( m_CurClsNameStrPt, p_InfoStrPt );
            Message p_MessagePt = new Message();
            p_MessagePt.what = ConfigActivityHandler.Msg.ShowLog.ordinal();
            p_MessagePt.obj = p_InfoStrPt;
            m_MainActivityHandlerPt.sendMessage( p_MessagePt );
        }

        //重置设置。
        AndrdAdoVdoTkbkStng.ResetStng( this );

        //读取设置。
        if( new File( m_ExternalDirFullAbsPathStrPt + "/Stng.xml" ).exists() ) AndrdAdoVdoTkbkStng.ReadStngFromXmlFile( this );
    }

    //主界面的从遮挡恢复消息。
    @Override public void onStart()
    {
        super.onStart();
        Log.i( m_CurClsNameStrPt, "onStart" );
    }

    //主界面从后台恢复消息。
    @Override public void onRestart()
    {
        super.onRestart();
        Log.i( m_CurClsNameStrPt, "onRestart" );
    }

    //主界面恢复运行消息。
    @Override public void onResume()
    {
        super.onResume();
        Log.i( m_CurClsNameStrPt, "onResume" );
    }

    //主界面被遮挡消息。
    @Override public void onPause()
    {
        super.onPause();
        Log.i( m_CurClsNameStrPt, "onPause" );
    }

    //主界面转入后台消息。
    @Override public void onStop()
    {
        super.onStop();
        Log.i( m_CurClsNameStrPt, "onStop" );
    }

    //主界面被销毁消息。
    @Override public void onDestroy()
    {
        super.onDestroy();
        Log.i( m_CurClsNameStrPt, "onDestroy" );
    }

    //主界面返回键消息。
    @Override public void onBackPressed()
    {
        Log.i( m_CurClsNameStrPt, "onBackPressed" );

        if( m_CurActivityLyotViewPt == m_MainLyotViewPt )
        {
            Log.i( m_CurClsNameStrPt, "用户在主界面按下返回键，本软件退出。" );
            if( m_MyMediaPocsThrdPt != null )
            {
                Log.i( m_CurClsNameStrPt, "开始请求并等待媒体处理线程退出。" );
                m_MyMediaPocsThrdPt.m_IsInterrupt = 1;
                m_MyMediaPocsThrdPt.RqirExit( 1, 1 );
                Log.i( m_CurClsNameStrPt, "结束请求并等待媒体处理线程退出。" );
            }
            System.exit(0);
        }
        else if( m_CurActivityLyotViewPt == m_XfrPrtclStngLyotViewPt )
        {
            //传输协议设置确定按钮。
            OnClickXfrPrtclStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_StngLyotViewPt )
        {
            //设置布局确定按钮。
            OnClickStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_AjbStngLyotViewPt )
        {
            //音频自适应抖动缓冲器设置布局确定按钮。
            this.OnClickAjbStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SaveStsToTxtFileStngLyotViewPt )
        {
            //保存状态到Txt文件设置布局确定按钮
            this.OnClickSaveStsToTxtFileStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt )
        {
            //保存音视频输入输出到Avi文件设置布局确定按钮。
            this.OnClickSaveAdoVdoInptOtptToAviFileStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SpeexAecStngLyotViewPt )
        {
            //Speex声学回音消除器设置布局确定按钮。
            OnClickSpeexAecStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_WebRtcAecmStngLyotViewPt )
        {
            //WebRtc定点版声学回音消除器设置布局确定按钮。
            OnClickWebRtcAecmStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_WebRtcAecStngLyotViewPt )
        {
            //WebRtc浮点版声学回音消除器设置布局确定按钮。
            OnClickWebRtcAecStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SpeexWebRtcAecStngLyotViewPt )
        {
            //SpeexWebRtc三重声学回音消除器设置布局确定按钮。
            OnClickSpeexWebRtcAecStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SpeexPrpocsNsStngLyotViewPt )
        {
            //Speex预处理器的噪音抑制设置布局确定按钮。
            OnClickSpeexPrpocsNsStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_WebRtcNsxStngLyotViewPt )
        {
            //WebRtc定点版噪音抑制器设置布局确定按钮。
            OnClickWebRtcNsxStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_WebRtcNsStngLyotViewPt )
        {
            //WebRtc浮点版噪音抑制器设置布局确定按钮。
            OnClickWebRtcNsStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SpeexPrpocsStngLyotViewPt )
        {
            //Speex预处理器的其他功能设置布局确定按钮。
            OnClickSpeexPrpocsStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SpeexCodecStngLyotViewPt )
        {
            //Speex编解码器设置布局确定按钮。
            OnClickSpeexCodecStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SaveAdoInptOtptToWaveFileStngLyotViewPt )
        {
            //保存音频输入输出到Wave文件设置布局确定按钮。
            OnClickSaveAdoInptOtptToWaveFileStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_OpenH264CodecStngLyotViewPt )
        {
            //OpenH264编解码器设置布局确定按钮。
            OnClickOpenH264CodecStngOkBtn( null );
        }
        else if( m_CurActivityLyotViewPt == m_SystemH264CodecStngLyotViewPt )
        {
            //系统自带H264编解码器设置布局确定按钮。
            OnClickSystemH264CodecStngOkBtn( null );
        }
    }

    //主界面横竖屏切换消息。
    @Override public void onConfigurationChanged( Configuration newConfig )
    {
        super.onConfigurationChanged( newConfig );

        if( m_MyMediaPocsThrdPt != null && m_MyMediaPocsThrdPt.m_VdoInptPt.m_IsInit != 0 ) //如果媒体处理线程已经启动，且已初始化视频输入。
        {
            m_MyMediaPocsThrdPt.SetVdoInpt(
                    m_MyMediaPocsThrdPt.m_VdoInptPt.m_MaxSmplRate,
                    m_MyMediaPocsThrdPt.m_VdoInptPt.m_FrmWidth,
                    m_MyMediaPocsThrdPt.m_VdoInptPt.m_FrmHeight,
                    getWindowManager().getDefaultDisplay().getRotation() * 90,
                    ( ( HTSurfaceView ) findViewById( R.id.VdoInptPrvwSurfaceId ) ) );
        }
    }

    //传输协议设置按钮。
    public void OnClickXfrPrtclStngBtn( View ViewPt )
    {
        setContentView( m_XfrPrtclStngLyotViewPt );
        m_CurActivityLyotViewPt = m_XfrPrtclStngLyotViewPt;
    }

    //使用音频对讲模式单选按钮。
    public void OnClickUseAdoTkbkModeRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.SendUserMsg( MyMediaPocsThrd.UserMsgTyp.LclTkbkMode, MyMediaPocsThrd.TkbkMode.Ado );
        }
    }

    //使用视频对讲模式按钮。
    public void OnClickUseVdoTkbkModeRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.SendUserMsg( MyMediaPocsThrd.UserMsgTyp.LclTkbkMode, MyMediaPocsThrd.TkbkMode.Vdo );
        }
    }

    //使用音视频对讲模式按钮。
    public void OnClickUseAdoVdoTkbkModeRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.SendUserMsg( MyMediaPocsThrd.UserMsgTyp.LclTkbkMode, MyMediaPocsThrd.TkbkMode.AdoVdo );
        }
    }

    //使用扬声器单选按钮。
    public void onClickUseSpeakerRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.AdoOtptSetUseDvc( 0, 0 );
        }
    }

    //使用听筒或耳机单选按钮。
    public void onClickUseHeadsetRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.AdoOtptSetUseDvc( 1, 0 );
        }
    }

    //使用前置摄像头单选按钮。
    public void onClickUseFrontCamereRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.VdoInptSetUseDvc( 0, -1, -1 );
        }
    }

    //使用后置摄像头单选按钮。
    public void onClickUseBackCamereRdBtn( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.VdoInptSetUseDvc( 1, -1, -1 );
        }
    }

    //音频输入是否静音复选框。
    public void onClickAdoInptIsMuteCkBox( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.AdoInptSetIsMute( ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.AdoInptIsMuteCkBoxId ) ).isChecked() ) ? 1 : 0 );
        }
    }

    //音频输出是否静音复选框。
    public void onClickAdoOtptIsMuteCkBox( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.AdoOtptSetIsMute( ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.AdoOtptIsMuteCkBoxId ) ).isChecked() ) ? 1 : 0 );
        }
    }

    //视频输入是否黑屏复选框。
    public void onClickVdoInptIsBlackCkBox( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.VdoInptSetIsBlack( ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.VdoInptIsBlackCkBoxId ) ).isChecked() ) ? 1 : 0 );
        }
    }

    //视频输出是否黑屏复选框。
    public void onClickVdoOtptIsBlackCkBox( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            m_MyMediaPocsThrdPt.VdoOtptSetStrmIsBlack( 0, ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.VdoOtptIsBlackCkBoxId ) ).isChecked() ) ? 1 : 0 );
        }
    }

    //是否绘制音频波形到Surface复选框。
    public void onClickIsDrawAdoWavfmToSurfaceCkBox( View ViewPt )
    {
        if( m_MyMediaPocsThrdPt != null )
        {
            //设置音频输入是否绘制音频波形到Surface。
            m_MyMediaPocsThrdPt.AdoInptSetIsDrawAdoWavfmToSurface(
                    ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.IsDrawAdoWavfmToSurfaceCkBoxId ) ).isChecked() ) ? 1 : 0,
                    ( ( SurfaceView )findViewById( R.id.AdoInptWavfmSurfaceId ) ),
                    ( ( SurfaceView )findViewById( R.id.AdoRsltWavfmSurfaceId ) ) );

            //设置音频输出是否绘制音频波形到Surface。
            m_MyMediaPocsThrdPt.AdoOtptSetIsDrawAdoWavfmToSurface(
                    ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.IsDrawAdoWavfmToSurfaceCkBoxId ) ).isChecked() ) ? 1 : 0,
                    ( ( SurfaceView )findViewById( R.id.AdoOtptWavfmSurfaceId ) ) );
        }
    }

    //创建服务器和连接服务器按钮。
    public void OnClickCreateSrvrAndCnctSrvrBtn( View ViewPt )
    {
        int p_Rslt = -1; //存放本函数执行结果的值，为0表示成功，为非0表示失败。

        Out:
        {
            if( m_MyMediaPocsThrdPt == null ) //如果媒体处理线程还没有启动。
            {
                Log.i( m_CurClsNameStrPt, "开始启动媒体处理线程。" );

                //创建媒体处理线程。
                m_MyMediaPocsThrdPt = new MyMediaPocsThrd( this, m_MainActivityHandlerPt );

                //设置网络。
                {
                    //设置IP地址字符串。
                    m_MyMediaPocsThrdPt.m_NtwkPt.m_IPAddrStrPt = ( ( EditText ) m_MainLyotViewPt.findViewById( R.id.IPAddrEdTxtId ) ).getText().toString();

                    //设置端口字符串。
                    m_MyMediaPocsThrdPt.m_NtwkPt.m_PortStrPt = ( ( EditText ) m_MainLyotViewPt.findViewById( R.id.PortEdTxtId ) ).getText().toString();

                    //设置使用什么传输协议。
                    m_MyMediaPocsThrdPt.m_NtwkPt.m_UseWhatXfrPrtcl = ( ( ( RadioButton ) m_MainLyotViewPt.findViewById( R.id.UseTcpPrtclRdBtnId ) ).isChecked() ) ? 0 : 1;

                    //设置传输模式。
                    m_MyMediaPocsThrdPt.m_NtwkPt.m_XfrMode = ( ( ( RadioButton ) m_XfrPrtclStngLyotViewPt.findViewById( R.id.UsePttRdBtnId ) ).isChecked() ) ? 0 : 1;

                    //设置最大连接次数。
                    try
                    {
                        m_MyMediaPocsThrdPt.m_NtwkPt.m_MaxCnctTimes = Integer.parseInt( ( ( TextView ) m_XfrPrtclStngLyotViewPt.findViewById( R.id.MaxCnctTimesEdTxtId ) ).getText().toString() );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }

                    //设置创建服务端或者客户端标记。
                    m_MyMediaPocsThrdPt.m_NtwkPt.m_IsCreateSrvrOrClnt = ( ViewPt.getId() == R.id.CreateSrvrBtnId ) ? 1 : 0; //标记创建服务端接受客户端。

                    //设置是否自动允许连接。
                    m_MyMediaPocsThrdPt.m_IsAutoAllowCnct = ( ( ( CheckBox ) m_XfrPrtclStngLyotViewPt.findViewById( R.id.IsAutoAllowCnctCkBoxId ) ).isChecked() ) ? 1 : 0;
                }

                //设置是否使用容器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseCntnrRecvOtptFrmRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.m_UseWhatRecvOtptFrm = 0;
                }

                //设置是否使用自适应抖动缓冲器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAjbRecvOtptFrmRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.m_UseWhatRecvOtptFrm = 1;

                    try
                    {
                        m_MyMediaPocsThrdPt.m_AAjbPt.m_MinNeedBufFrmCnt = Integer.parseInt( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.AAjbMinNeedBufFrmCntEdTxtId ) ).getText().toString() );
                        m_MyMediaPocsThrdPt.m_AAjbPt.m_MaxNeedBufFrmCnt = Integer.parseInt( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.AAjbMaxNeedBufFrmCntEdTxtId ) ).getText().toString() );
                        m_MyMediaPocsThrdPt.m_AAjbPt.m_MaxCntuLostFrmCnt = Integer.parseInt( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.AAjbMaxCntuLostFrmCntEdTxtId ) ).getText().toString() );
                        m_MyMediaPocsThrdPt.m_AAjbPt.m_AdaptSensitivity = Float.parseFloat( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.AAjbAdaptSensitivityEdTxtId ) ).getText().toString() );

                        m_MyMediaPocsThrdPt.m_VAjbPt.m_MinNeedBufFrmCnt = Integer.parseInt( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.VAjbMinNeedBufFrmCntEdTxtId ) ).getText().toString() );
                        m_MyMediaPocsThrdPt.m_VAjbPt.m_MaxNeedBufFrmCnt = Integer.parseInt( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.VAjbMaxNeedBufFrmCntEdTxtId ) ).getText().toString() );
                        m_MyMediaPocsThrdPt.m_VAjbPt.m_AdaptSensitivity = Float.parseFloat( ( ( TextView ) m_AjbStngLyotViewPt.findViewById( R.id.VAjbAdaptSensitivityEdTxtId ) ).getText().toString() );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置是否打印Logcat日志、显示Toast。
                m_MyMediaPocsThrdPt.SetIsPrintLogcatShowToast(
                        ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsPrintLogcatShowToastCkBoxId ) ).isChecked() ) ? 1 : 0,
                        ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsPrintLogcatShowToastCkBoxId ) ).isChecked() ) ? 1 : 0,
                        this );

                //设置是否使用唤醒锁。
                m_MyMediaPocsThrdPt.SetIsUseWakeLock( ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsUseWakeLockCkBoxId ) ).isChecked() ) ? 1 : 0 );

                //设置是否保存音视频输入输出到Avi文件。
                if( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsSaveAdoVdoInptOtptToAviFileCkBoxId ) ).isChecked() )
                {
                    String p_FullPathStrPt;

                    p_FullPathStrPt = ( ( EditText ) m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt.findViewById( R.id.SaveAdoVdoInptOtptToAviFileFullPathEdTxtId ) ).getText().toString();
                    if( p_FullPathStrPt.charAt( 0 ) != '/' ) p_FullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/" + p_FullPathStrPt;

                    try
                    {
                        m_MyMediaPocsThrdPt.SetIsSaveAdoVdoInptOtptToAviFile(
                                p_FullPathStrPt,
                                Integer.parseInt( ( ( TextView ) m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt.findViewById( R.id.SaveAdoVdoInptOtptToAviFileWrBufSzBytEdTxtId ) ).getText().toString() ),
                                ( ( ( CheckBox ) m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt.findViewById( R.id.SaveAdoVdoInptOtptToAviFileIsSaveAdoInptCkBoxId ) ).isChecked() ) ? 1 : 0,
                                ( ( ( CheckBox ) m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt.findViewById( R.id.SaveAdoVdoInptOtptToAviFileIsSaveAdoOtptCkBoxId ) ).isChecked() ) ? 1 : 0,
                                ( ( ( CheckBox ) m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt.findViewById( R.id.SaveAdoVdoInptOtptToAviFileIsSaveVdoInptCkBoxId ) ).isChecked() ) ? 1 : 0,
                                ( ( ( CheckBox ) m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt.findViewById( R.id.SaveAdoVdoInptOtptToAviFileIsSaveVdoOtptCkBoxId ) ).isChecked() ) ? 1 : 0 );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入。
                m_MyMediaPocsThrdPt.SetAdoInpt(
                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate8000RdBtnId ) ).isChecked() ) ? 8000 :
                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate16000RdBtnId ) ).isChecked() ) ? 16000 :
                                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate32000RdBtnId ) ).isChecked() ) ? 32000 :
                                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate48000RdBtnId ) ).isChecked() ) ? 48000 : 0,
                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoFrmLen10msRdBtnId ) ).isChecked() ) ? 10 :
                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoFrmLen20msRdBtnId ) ).isChecked() ) ? 20 :
                                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoFrmLen30msRdBtnId ) ).isChecked() ) ? 30 : 0 );

                //设置音频输入是否使用系统自带声学回音消除器、噪音抑制器和自动增益控制器。
                m_MyMediaPocsThrdPt.AdoInptSetIsUseSystemAecNsAgc(
                        ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsUseSystemAecNsAgcCkBoxId ) ).isChecked() ) ? 1 : 0 );

                if( m_MyMediaPocsThrdPt.m_NtwkPt.m_XfrMode == 0 ) //如果传输模式为实时半双工（一键通）。
                {
                    m_MyMediaPocsThrdPt.AdoInptSetUseNoAec();
                }
                else //如果传输模式为实时全双工。
                {
                    //设置音频输入是否不使用声学回音消除器。
                    if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseNoAecRdBtnId ) ).isChecked() )
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetUseNoAec();
                    }

                    //设置音频输入是否使用Speex声学回音消除器。
                    if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSpeexAecRdBtnId ) ).isChecked() )
                    {
                        try
                        {
                            m_MyMediaPocsThrdPt.AdoInptSetUseSpeexAec(
                                    Integer.parseInt( ( ( TextView ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecFilterLenMsecEdTxtId ) ).getText().toString() ),
                                    ( ( ( CheckBox ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecIsUseRecCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    Float.parseFloat( ( ( TextView ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecEchoMutpEdTxtId ) ).getText().toString() ),
                                    Float.parseFloat( ( ( TextView ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecEchoCntuEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecEchoSupesEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecEchoSupesActEdTxtId ) ).getText().toString() ),
                                    ( ( ( CheckBox ) m_SpeexAecStngLyotViewPt.findViewById( R.id.SpeexAecIsSaveMemFileCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    m_ExternalDirFullAbsPathStrPt + "/SpeexAecMem" );
                        }
                        catch( NumberFormatException e )
                        {
                            Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                            break Out;
                        }
                    }

                    //设置音频输入是否使用WebRtc定点版声学回音消除器。
                    if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseWebRtcAecmRdBtnId ) ).isChecked() )
                    {
                        try
                        {
                            m_MyMediaPocsThrdPt.AdoInptSetUseWebRtcAecm(
                                    ( ( ( CheckBox ) m_WebRtcAecmStngLyotViewPt.findViewById( R.id.WebRtcAecmIsUseCNGModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    Integer.parseInt( ( ( TextView ) m_WebRtcAecmStngLyotViewPt.findViewById( R.id.WebRtcAecmEchoModeEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_WebRtcAecmStngLyotViewPt.findViewById( R.id.WebRtcAecmDelayEdTxtId ) ).getText().toString() ) );
                        }
                        catch( NumberFormatException e )
                        {
                            Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                            break Out;
                        }
                    }

                    //设置音频输入是否使用WebRtc浮点版声学回音消除器。
                    if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseWebRtcAecRdBtnId ) ).isChecked() )
                    {
                        try
                        {
                            m_MyMediaPocsThrdPt.AdoInptSetUseWebRtcAec(
                                    Integer.parseInt( ( ( TextView ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecEchoModeEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecDelayEdTxtId ) ).getText().toString() ),
                                    ( ( ( CheckBox ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecIsUseDelayAgstcModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecIsUseExtdFilterModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecIsUseRefinedFilterAdaptAecModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecIsUseAdaptAdjDelayCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_WebRtcAecStngLyotViewPt.findViewById( R.id.WebRtcAecIsSaveMemFileCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    m_ExternalDirFullAbsPathStrPt + "/WebRtcAecMem" );
                        }
                        catch( NumberFormatException e )
                        {
                            Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                            break Out;
                        }
                    }

                    //设置音频输入是否使用SpeexWebRtc三重声学回音消除器。
                    if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSpeexWebRtcAecRdBtnId ) ).isChecked() )
                    {
                        try
                        {
                            m_MyMediaPocsThrdPt.AdoInptSetUseSpeexWebRtcAec(
                                    ( ( RadioButton ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWorkModeSpeexAecWebRtcAecmRdBtnId ) ).isChecked() ? 1 :
                                            ( ( RadioButton ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWorkModeWebRtcAecmWebRtcAecRdBtnId ) ).isChecked() ? 2 :
                                                    ( ( RadioButton ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWorkModeSpeexAecWebRtcAecmWebRtcAecRdBtnId ) ).isChecked() ? 3 : 0,
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSpeexAecFilterLenMsecEdTxtId ) ).getText().toString() ),
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSpeexAecIsUseRecCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    Float.parseFloat( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSpeexAecEchoMutpEdTxtId ) ).getText().toString() ),
                                    Float.parseFloat( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSpeexAecEchoCntuEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSpeexAecEchoSupesEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSpeexAecEchoSupesActEdTxtId ) ).getText().toString() ),
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecmIsUseCNGModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecmEchoModeEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecmDelayEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecEchoModeEdTxtId ) ).getText().toString() ),
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecDelayEdTxtId ) ).getText().toString() ),
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecIsUseDelayAgstcModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecIsUseExtdFilterModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecIsUseRefinedFilterAdaptAecModeCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecWebRtcAecIsUseAdaptAdjDelayCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    ( ( ( CheckBox ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecIsUseSameRoomAecCkBoxId ) ).isChecked() ) ? 1 : 0,
                                    Integer.parseInt( ( ( TextView ) m_SpeexWebRtcAecStngLyotViewPt.findViewById( R.id.SpeexWebRtcAecSameRoomEchoMinDelayEdTxtId ) ).getText().toString() ) );
                        }
                        catch( NumberFormatException e )
                        {
                            Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                            break Out;
                        }
                    }
                }

                //设置音频输入是否不使用噪音抑制器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseNoNsRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.AdoInptSetUseNoNs();
                }

                //设置音频输入是否使用Speex预处理器的噪音抑制。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSpeexPrpocsNsRdBtnId ) ).isChecked() )
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetUseSpeexPrpocsNs(
                                ( ( ( CheckBox ) m_SpeexPrpocsNsStngLyotViewPt.findViewById( R.id.SpeexPrpocsIsUseNsCkBoxId ) ).isChecked() ) ? 1 : 0,
                                Integer.parseInt( ( ( TextView ) m_SpeexPrpocsNsStngLyotViewPt.findViewById( R.id.SpeexPrpocsNoiseSupesEdTxtId ) ).getText().toString() ),
                                ( ( ( CheckBox ) m_SpeexPrpocsNsStngLyotViewPt.findViewById( R.id.SpeexPrpocsIsUseDereverbCkBoxId ) ).isChecked() ) ? 1 : 0 );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入是否使用WebRtc定点版噪音抑制器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseWebRtcNsxRdBtnId ) ).isChecked() )
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetUseWebRtcNsx(
                                Integer.parseInt( ( ( TextView ) m_WebRtcNsxStngLyotViewPt.findViewById( R.id.WebRtcNsxPolicyModeEdTxtId ) ).getText().toString() ) );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入是否使用WebRtc浮点版噪音抑制器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseWebRtcNsRdBtnId ) ).isChecked() )
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetUseWebRtcNs(
                                Integer.parseInt( ( ( TextView ) m_WebRtcNsStngLyotViewPt.findViewById( R.id.WebRtcNsPolicyModeEdTxtId ) ).getText().toString() ) );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入是否使用RNNoise噪音抑制器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseRNNoiseRdBtnId ) ).isChecked() )
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetUseRNNoise();
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入是否使用Speex预处理器。
                try
                {
                    m_MyMediaPocsThrdPt.AdoInptSetIsUseSpeexPrpocs(
                            ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsUseSpeexPrpocsCkBoxId ) ).isChecked() ) ? 1 : 0,
                            ( ( ( CheckBox ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsIsUseVadCkBoxId ) ).isChecked() ) ? 1 : 0,
                            Integer.parseInt( ( ( TextView ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsVadProbStartEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsVadProbCntuEdTxtId ) ).getText().toString() ),
                            ( ( ( CheckBox ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsIsUseAgcCkBoxId ) ).isChecked() ) ? 1 : 0,
                            Integer.parseInt( ( ( TextView ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsAgcLevelEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsAgcIncrementEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsAgcDecrementEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_SpeexPrpocsStngLyotViewPt.findViewById( R.id.SpeexPrpocsAgcMaxGainEdTxtId ) ).getText().toString() ) );
                }
                catch( NumberFormatException e )
                {
                    Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                    break Out;
                }

                //设置音频输入是否使用PCM原始数据。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UsePcmRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.AdoInptSetUsePcm();
                }

                //设置音频输入是否使用Speex编码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSpeexCodecRdBtnId ) ).isChecked() )
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetUseSpeexEncd(
                                ( ( ( RadioButton ) m_SpeexCodecStngLyotViewPt.findViewById( R.id.SpeexEncdUseCbrRdBtnId ) ).isChecked() ) ? 0 : 1,
                                Integer.parseInt( ( ( TextView ) m_SpeexCodecStngLyotViewPt.findViewById( R.id.SpeexEncdQualtEdTxtId ) ).getText().toString() ),
                                Integer.parseInt( ( ( TextView ) m_SpeexCodecStngLyotViewPt.findViewById( R.id.SpeexEncdCmplxtEdTxtId ) ).getText().toString() ),
                                Integer.parseInt( ( ( TextView ) m_SpeexCodecStngLyotViewPt.findViewById( R.id.SpeexEncdPlcExptLossRateEdTxtId ) ).getText().toString() ) );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入是否使用Opus编码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseOpusCodecRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.AdoInptSetUseOpusEncd();
                }

                //设置音频输入是否保存音频到Wave文件。
                if( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsSaveAdoInptOtptToWaveFileCkBoxId ) ).isChecked() &&
                        ( ( CheckBox ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileIsSaveAdoInptCkBoxId ) ).isChecked() )
                {
                    String p_AdoInptSrcFullPathStrPt;
                    String p_AdoInptRsltFullPathStrPt;

                    p_AdoInptSrcFullPathStrPt = ( ( EditText ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileAdoInptSrcFullPathEdTxtId ) ).getText().toString();
                    if( p_AdoInptSrcFullPathStrPt.charAt( 0 ) != '/' ) p_AdoInptSrcFullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/" + p_AdoInptSrcFullPathStrPt;
                    p_AdoInptRsltFullPathStrPt = ( ( EditText ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileAdoInptRsltFullPathEdTxtId ) ).getText().toString();
                    if( p_AdoInptRsltFullPathStrPt.charAt( 0 ) != '/' ) p_AdoInptRsltFullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/" + p_AdoInptRsltFullPathStrPt;

                    try
                    {
                        m_MyMediaPocsThrdPt.AdoInptSetIsSaveAdoToWaveFile(
                                ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsSaveAdoInptOtptToWaveFileCkBoxId ) ).isChecked() ) ? 1 : 0,
                                p_AdoInptSrcFullPathStrPt,
                                p_AdoInptRsltFullPathStrPt,
                                Integer.parseInt( ( ( TextView ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileWrBufSzBytEdTxtId ) ).getText().toString() ) );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输入是否绘制音频波形到Surface。
                m_MyMediaPocsThrdPt.AdoInptSetIsDrawAdoWavfmToSurface(
                        ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.IsDrawAdoWavfmToSurfaceCkBoxId ) ).isChecked() ) ? 1 : 0,
                        ( ( SurfaceView )findViewById( R.id.AdoInptWavfmSurfaceId ) ),
                        ( ( SurfaceView )findViewById( R.id.AdoRsltWavfmSurfaceId ) ) );

                //设置音频输入是否静音。
                m_MyMediaPocsThrdPt.AdoInptSetIsMute(
                        ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.AdoInptIsMuteCkBoxId ) ).isChecked() ) ? 1 : 0 );

                //设置音频输出。
                m_MyMediaPocsThrdPt.SetAdoOtpt(
                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate8000RdBtnId ) ).isChecked() ) ? 8000 :
                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate16000RdBtnId ) ).isChecked() ) ? 16000 :
                                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate32000RdBtnId ) ).isChecked() ) ? 32000 :
                                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoSmplRate48000RdBtnId ) ).isChecked() ) ? 48000 : 0,
                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoFrmLen10msRdBtnId ) ).isChecked() ) ? 10 :
                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoFrmLen20msRdBtnId ) ).isChecked() ) ? 20 :
                                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseAdoFrmLen30msRdBtnId ) ).isChecked() ) ? 30 : 0 );
                m_MyMediaPocsThrdPt.AddAdoOtptStrm( 0 );

                //设置音频输出是否使用PCM原始数据。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UsePcmRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.AdoOtptSetStrmUsePcm( 0 );
                }

                //设置音频输出是否使用Speex解码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSpeexCodecRdBtnId ) ).isChecked() )
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.AdoOtptSetStrmUseSpeexDecd(
                                0,
                                ( ( ( CheckBox ) m_SpeexCodecStngLyotViewPt.findViewById( R.id.SpeexDecdIsUsePrcplEnhsmtCkBoxId ) ).isChecked() ) ? 1 : 0 );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输出是否使用Opus解码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseOpusCodecRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.AdoOtptSetStrmUseOpusDecd( 0 );
                }

                //设置音频输出流是否使用。
                m_MyMediaPocsThrdPt.AdoOtptSetStrmIsUse( 0, 1 );

                //设置音频输出是否保存音频到Wave文件。
                if( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsSaveAdoInptOtptToWaveFileCkBoxId ) ).isChecked() &&
                        ( ( CheckBox ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileIsSaveAdoOtptCkBoxId ) ).isChecked() )
                {
                    String p_AdoOtptSrcFullPathStrPt;

                    p_AdoOtptSrcFullPathStrPt = ( ( EditText ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileAdoOtptSrcFullPathEdTxtId ) ).getText().toString();
                    if( p_AdoOtptSrcFullPathStrPt.charAt( 0 ) != '/' ) p_AdoOtptSrcFullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/" + p_AdoOtptSrcFullPathStrPt;

                    try
                    {
                        m_MyMediaPocsThrdPt.AdoOtptSetIsSaveAdoToWaveFile(
                                ( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsSaveAdoInptOtptToWaveFileCkBoxId ) ).isChecked() ) ? 1 : 0,
                                p_AdoOtptSrcFullPathStrPt,
                                Integer.parseInt( ( ( TextView ) m_SaveAdoInptOtptToWaveFileStngLyotViewPt.findViewById( R.id.SaveAdoInptOtptToWaveFileWrBufSzBytEdTxtId ) ).getText().toString() ) );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置音频输出是否绘制音频波形到Surface。
                m_MyMediaPocsThrdPt.AdoOtptSetIsDrawAdoWavfmToSurface(
                        ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.IsDrawAdoWavfmToSurfaceCkBoxId ) ).isChecked() ) ? 1 : 0,
                        ( ( SurfaceView )findViewById( R.id.AdoOtptWavfmSurfaceId ) ) );

                //设置音频输出使用的设备。
                m_MyMediaPocsThrdPt.AdoOtptSetUseDvc(
                        ( ( ( RadioButton ) m_MainLyotViewPt.findViewById( R.id.UseSpeakerRdBtnId ) ).isChecked() ) ? 0 : 1,
                        0 );

                //设置音频输出是否静音。
                m_MyMediaPocsThrdPt.AdoOtptSetIsMute(
                        ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.AdoOtptIsMuteCkBoxId ) ).isChecked() ) ? 1 : 0 );

                //设置视频输入。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoFrmSzPrsetRdBtnId ) ).isChecked() ) //如果要使用预设的帧的大小。
                {
                    m_MyMediaPocsThrdPt.SetVdoInpt(
                            ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate12RdBtnId ) ).isChecked() ) ? 12 :
                                    ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate15RdBtnId ) ).isChecked() ) ? 15 :
                                            ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate24RdBtnId ) ).isChecked() ) ? 24 :
                                                    ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate30RdBtnId ) ).isChecked() ) ? 30 : 0,
                            ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 0 ) ? 120 :
                                    ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 1 ) ? 240 :
                                            ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 2 ) ? 480 :
                                                    ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 3 ) ? 960 : 0,
                            ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 0 ) ? 160 :
                                    ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 1 ) ? 320 :
                                            ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 2 ) ? 640 :
                                                    ( ( ( Spinner ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzPrsetSpinnerId ) ).getSelectedItemPosition() == 3 ) ? 1280 : 0,
                            getWindowManager().getDefaultDisplay().getRotation() * 90,
                            ( ( HTSurfaceView ) findViewById( R.id.VdoInptPrvwSurfaceId ) ) );
                }
                else //如果要使用其他的帧的大小。
                {
                    try
                    {
                        m_MyMediaPocsThrdPt.SetVdoInpt(
                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate12RdBtnId ) ).isChecked() ) ? 12 :
                                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate15RdBtnId ) ).isChecked() ) ? 15 :
                                                ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate24RdBtnId ) ).isChecked() ) ? 24 :
                                                        ( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseVdoSmplRate30RdBtnId ) ).isChecked() ) ? 30 : 0,
                                Integer.parseInt( ( ( TextView ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzOtherWidthEdTxtId ) ).getText().toString() ),
                                Integer.parseInt( ( ( TextView ) m_StngLyotViewPt.findViewById( R.id.VdoFrmSzOtherHeightEdTxtId ) ).getText().toString() ),
                                getWindowManager().getDefaultDisplay().getRotation() * 90,
                                ( ( HTSurfaceView ) findViewById( R.id.VdoInptPrvwSurfaceId ) ) );
                    }
                    catch( NumberFormatException e )
                    {
                        Toast.makeText( this, "请输入数字", Toast.LENGTH_LONG ).show();
                        break Out;
                    }
                }

                //设置视频输入是否使用Yu12原始数据。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseYu12RdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.VdoInptSetUseYu12();
                }

                //设置视频输入是否使用OpenH264编码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseOpenH264CodecRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.VdoInptSetUseOpenH264Encd(
                            Integer.parseInt( ( ( TextView ) m_OpenH264CodecStngLyotViewPt.findViewById( R.id.OpenH264EncdVdoTypeEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_OpenH264CodecStngLyotViewPt.findViewById( R.id.OpenH264EncdEncdBitrateEdTxtId ) ).getText().toString() ) * 1024 * 8,
                            Integer.parseInt( ( ( TextView ) m_OpenH264CodecStngLyotViewPt.findViewById( R.id.OpenH264EncdBitrateCtrlModeEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_OpenH264CodecStngLyotViewPt.findViewById( R.id.OpenH264EncdIDRFrmIntvlEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_OpenH264CodecStngLyotViewPt.findViewById( R.id.OpenH264EncdCmplxtEdTxtId ) ).getText().toString() ) );
                }

                //设置视频输入是否使用系统自带H264编码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSystemH264CodecRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.VdoInptSetUseSystemH264Encd(
                            Integer.parseInt( ( ( TextView ) m_SystemH264CodecStngLyotViewPt.findViewById( R.id.SystemH264EncdEncdBitrateEdTxtId ) ).getText().toString() ) * 1024 * 8,
                            Integer.parseInt( ( ( TextView ) m_SystemH264CodecStngLyotViewPt.findViewById( R.id.SystemH264EncdBitrateCtrlModeEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_SystemH264CodecStngLyotViewPt.findViewById( R.id.SystemH264EncdIDRFrmIntvlEdTxtId ) ).getText().toString() ),
                            Integer.parseInt( ( ( TextView ) m_SystemH264CodecStngLyotViewPt.findViewById( R.id.SystemH264EncdCmplxtEdTxtId ) ).getText().toString() ) );
                }

                //设置视频输入使用的设备。
                m_MyMediaPocsThrdPt.VdoInptSetUseDvc(
                        ( ( ( RadioButton ) m_MainLyotViewPt.findViewById( R.id.UseFrontCamereRdBtnId ) ).isChecked() ) ? 0 : 1,
                        -1,
                        -1 );

                //设置视频输入是否黑屏。
                m_MyMediaPocsThrdPt.VdoInptSetIsBlack(
                        ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.VdoInptIsBlackCkBoxId ) ).isChecked() ) ? 1 : 0 );

                //设置视频输出。
                m_MyMediaPocsThrdPt.VdoOtptAddStrm( 0 );
                m_MyMediaPocsThrdPt.VdoOtptSetStrm(
                        0,
                        ( ( HTSurfaceView )findViewById( R.id.VdoOtptDspySurfaceId ) ) );

                //设置视频输出是否使用Yu12原始数据。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseYu12RdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.VdoOtptSetStrmUseYu12( 0 );
                }

                //设置视频输出是否使用OpenH264解码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseOpenH264CodecRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.VdoOtptSetStrmUseOpenH264Decd( 0, 0 );
                }

                //设置视频输出是否使用系统自带H264解码器。
                if( ( ( RadioButton ) m_StngLyotViewPt.findViewById( R.id.UseSystemH264CodecRdBtnId ) ).isChecked() )
                {
                    m_MyMediaPocsThrdPt.VdoOtptSetStrmUseSystemH264Decd( 0 );
                }

                //设置视频输出是否黑屏。
                m_MyMediaPocsThrdPt.VdoOtptSetStrmIsBlack(
                        0,
                        ( ( ( CheckBox ) m_MainLyotViewPt.findViewById( R.id.VdoOtptIsBlackCkBoxId ) ).isChecked() ) ? 1 : 0 );

                //设置视频输出流是否使用。
                m_MyMediaPocsThrdPt.VdoOtptSetStrmIsUse( 0, 1 );

                //设置本端对讲模式。
                m_MyMediaPocsThrdPt.SendUserMsg(
                        MyMediaPocsThrd.UserMsgTyp.LclTkbkMode,
                        ( ( ( RadioButton ) m_MainLyotViewPt.findViewById( R.id.UseAdoTkbkModeRdBtnId ) ).isChecked() ) ? MyMediaPocsThrd.TkbkMode.Ado :
                                ( ( ( RadioButton ) m_MainLyotViewPt.findViewById( R.id.UseVdoTkbkModeRdBtnId ) ).isChecked() ) ? MyMediaPocsThrd.TkbkMode.Vdo :
                                        ( ( ( RadioButton ) m_MainLyotViewPt.findViewById( R.id.UseAdoVdoTkbkModeRdBtnId ) ).isChecked() ) ? MyMediaPocsThrd.TkbkMode.AdoVdo : MyMediaPocsThrd.TkbkMode.NoChg );

                //设置是否保存状态到Txt文件。
                if( ( ( CheckBox ) m_StngLyotViewPt.findViewById( R.id.IsSaveStsToTxtFileCkBoxId ) ).isChecked() )
                {
                    String p_FullPathStrPt;

                    p_FullPathStrPt = ( ( EditText ) m_SaveStsToTxtFileStngLyotViewPt.findViewById( R.id.SaveStsToTxtFileFullPathEdTxtId ) ).getText().toString();
                    if( p_FullPathStrPt.charAt( 0 ) != '/' ) p_FullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/" + p_FullPathStrPt;

                    m_MyMediaPocsThrdPt.SaveStsToTxtFile( p_FullPathStrPt );
                }

                //启动媒体处理线程。
                m_MyMediaPocsThrdPt.start();

                Log.i( m_CurClsNameStrPt, "启动媒体处理线程完毕。" );
            }
            else
            {
                Log.i( m_CurClsNameStrPt, "开始请求并等待媒体处理线程退出。" );
                m_MyMediaPocsThrdPt.m_IsInterrupt = 1;
                m_MyMediaPocsThrdPt.RqirExit( 1, 1 );
                Log.i( m_CurClsNameStrPt, "结束请求并等待媒体处理线程退出。" );
            }

            p_Rslt = 0;
        }

        if( p_Rslt != 0 ) //如果启动媒体处理线程失败。
        {
            m_MyMediaPocsThrdPt = null;
        }
    }

    //设置按钮。
    public void OnClickStngBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //保存设置按钮。
    public void OnClickSaveStngBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.SaveStngToXmlFile( this );
    }

    //读取设置按钮。
    public void OnClickReadStngBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.ReadStngFromXmlFile( this );
    }

    //删除设置按钮。
    public void OnClickDelStngBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.DelStngXmlFile( this );
    }

    //重置设置按钮。
    public void OnClickResetStngBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.ResetStng( this );
    }

    //必读说明按钮。
    public void OnClickReadMeBtn( View ViewPt )
    {
        startActivity( new Intent( Intent.ACTION_VIEW, Uri.parse( "https://github.com/cyz7758520/Android_audio_talkback_demo_program" ) ) );
    }

    //视频输入预览或视频输出显示Surface。
    public void onClickVdoInptPrvwAndVdoOtptDspySurface( View ViewPt )
    {
        if( ( ( LinearLayout )ViewPt.getParent().getParent() ).getOrientation() == LinearLayout.HORIZONTAL )
        {
            ( ( LinearLayout )ViewPt.getParent().getParent() ).setOrientation( LinearLayout.VERTICAL );
        }
        else
        {
            ( ( LinearLayout )ViewPt.getParent().getParent() ).setOrientation( LinearLayout.HORIZONTAL );
        }
    }

    //清空日志按钮。
    public void OnClickClearLogBtn( View ViewPt )
    {
        ( ( LinearLayout ) m_MainLyotViewPt.findViewById( R.id.LogLinearLyotId ) ).removeAllViews();
    }

    //一键即按即通按钮。
    @Override public boolean onTouch( View ViewPt, MotionEvent EventPt )
    {
        if( ViewPt.getId() == R.id.PttBtnId ) //如果是一键即按即通按钮。
        {
            switch( EventPt.getAction() )
            {
                case MotionEvent.ACTION_DOWN: //如果是按下消息。
                {
                    if( m_MyMediaPocsThrdPt != null )
                    {
                        m_MyMediaPocsThrdPt.SendUserMsg( MyMediaPocsThrd.UserMsgTyp.PttBtnDown );
                    }
                    break;
                }
                case MotionEvent.ACTION_UP: //如果是弹起消息。
                {
                    if( m_MyMediaPocsThrdPt != null )
                    {
                        m_MyMediaPocsThrdPt.SendUserMsg( MyMediaPocsThrd.UserMsgTyp.PttBtnUp );
                    }
                    break;
                }
            }
        }
        return false;
    }

    //音频自适应抖动缓冲器设置按钮。
    public void OnClickAjbStngBtn( View ViewPt )
    {
        setContentView( m_AjbStngLyotViewPt );
        m_CurActivityLyotViewPt = m_AjbStngLyotViewPt;
    }

    //保存状态到Txt文件设置按钮。
    public void OnClickSaveStsToTxtFileStngBtn( View ViewPt )
    {
        setContentView( m_SaveStsToTxtFileStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SaveStsToTxtFileStngLyotViewPt;
    }

    //保存音视频输入输出到Avi文件设置按钮。
    public void OnClickSaveAdoVdoInptOtptToAviFileStngBtn( View ViewPt )
    {
        setContentView( m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SaveAdoVdoInptOtptToAviFileStngLyotViewPt;
    }

    //效果等级：低。
    public void OnClickUseEffectLowRdBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.EffectLow( this );
    }

    //效果等级：中。
    public void OnClickUseEffectMidRdBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.EffectMid( this );
    }

    //效果等级：高。
    public void OnClickUseEffectHighRdBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.EffectHigh( this );
    }

    //效果等级：超。
    public void OnClickUseEffectSuperRdBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.EffectSuper( this );
    }

    //效果等级：特。
    public void OnClickUseEffectPremiumRdBtn( View ViewPt )
    {
        AndrdAdoVdoTkbkStng.EffectPremium( this );
    }

    //Speex声学回音消除器设置按钮。
    public void OnClickSpeexAecStngBtn( View ViewPt )
    {
        setContentView( m_SpeexAecStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SpeexAecStngLyotViewPt;
    }

    //WebRtc定点版声学回音消除器设置按钮。
    public void OnClickWebRtcAecmStngBtn( View ViewPt )
    {
        setContentView( m_WebRtcAecmStngLyotViewPt );
        m_CurActivityLyotViewPt = m_WebRtcAecmStngLyotViewPt;
    }

    //WebRtc浮点版声学回音消除器设置按钮。
    public void OnClickWebRtcAecStngBtn( View ViewPt )
    {
        setContentView( m_WebRtcAecStngLyotViewPt );
        m_CurActivityLyotViewPt = m_WebRtcAecStngLyotViewPt;
    }

    //SpeexWebRtc三重声学回音消除器设置按钮。
    public void OnClickSpeexWebRtcAecStngBtn( View ViewPt )
    {
        setContentView( m_SpeexWebRtcAecStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SpeexWebRtcAecStngLyotViewPt;
    }

    //Speex预处理器的噪音抑制设置按钮。
    public void OnClickSpeexPrpocsNsStngBtn( View ViewPt )
    {
        setContentView( m_SpeexPrpocsNsStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SpeexPrpocsNsStngLyotViewPt;
    }

    //WebRtc定点版噪音抑制器设置按钮。
    public void OnClickWebRtcNsxStngBtn( View ViewPt )
    {
        setContentView( m_WebRtcNsxStngLyotViewPt );
        m_CurActivityLyotViewPt = m_WebRtcNsxStngLyotViewPt;
    }

    //WebRtc浮点版噪音抑制器设置按钮。
    public void OnClickWebRtcNsStngBtn( View ViewPt )
    {
        setContentView( m_WebRtcNsStngLyotViewPt );
        m_CurActivityLyotViewPt = m_WebRtcNsStngLyotViewPt;
    }

    //Speex预处理器设置按钮。
    public void OnClickSpeexPrpocsStngBtn( View ViewPt )
    {
        setContentView( m_SpeexPrpocsStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SpeexPrpocsStngLyotViewPt;
    }

    //Speex编解码器设置按钮。
    public void OnClickSpeexCodecStngBtn( View ViewPt )
    {
        setContentView( m_SpeexCodecStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SpeexCodecStngLyotViewPt;
    }

    //Opus编解码器设置按钮。
    public void OnClickOpusCodecStngBtn( View ViewPt )
    {

    }

    //保存音频输入输出到Wave文件设置按钮。
    public void OnClickSaveAdoInptOtptToWaveFileStngBtn( View ViewPt )
    {
        setContentView( m_SaveAdoInptOtptToWaveFileStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SaveAdoInptOtptToWaveFileStngLyotViewPt;
    }

    //OpenH264编解码器设置按钮。
    public void OnClickOpenH264CodecStngBtn( View ViewPt )
    {
        setContentView( m_OpenH264CodecStngLyotViewPt );
        m_CurActivityLyotViewPt = m_OpenH264CodecStngLyotViewPt;
    }

    //系统自带H264编解码器设置按钮。
    public void OnClickSystemH264CodecStngBtn( View ViewPt )
    {
        setContentView( m_SystemH264CodecStngLyotViewPt );
        m_CurActivityLyotViewPt = m_SystemH264CodecStngLyotViewPt;
    }

    //传输协议设置确定按钮。
    public void OnClickXfrPrtclStngOkBtn( View ViewPt )
    {
        setContentView( m_MainLyotViewPt );
        m_CurActivityLyotViewPt = m_MainLyotViewPt;
    }

    //设置布局确定按钮。
    public void OnClickStngOkBtn( View ViewPt )
    {
        setContentView( m_MainLyotViewPt );
        m_CurActivityLyotViewPt = m_MainLyotViewPt;
    }

    //音频自适应抖动缓冲器设置布局确定按钮。
    public void OnClickAjbStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //保存状态到Txt文件设置布局确定按钮。
    public void OnClickSaveStsToTxtFileStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //保存音视频输入输出到Avi文件设置布局确定按钮。
    public void OnClickSaveAdoVdoInptOtptToAviFileStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //Speex声学回音消除器设置布局的删除内存块文件按钮。
    public void OnClickSpeexAecDelMemFileBtn( View ViewPt )
    {
        String p_SpeexAecMemFileFullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/SpeexAecMem";
        File file = new File( p_SpeexAecMemFileFullPathStrPt );
        if( file.exists() )
        {
            if( file.delete() )
            {
                Toast.makeText( this, "删除Speex声学回音消除器的内存块文件 " + p_SpeexAecMemFileFullPathStrPt + " 成功。", Toast.LENGTH_LONG ).show();
            }
            else
            {
                Toast.makeText( this, "删除Speex声学回音消除器的内存块文件 " + p_SpeexAecMemFileFullPathStrPt + " 失败。", Toast.LENGTH_LONG ).show();
            }
        }
        else
        {
            Toast.makeText( this, "Speex声学回音消除器的内存块文件 " + p_SpeexAecMemFileFullPathStrPt + " 不存在。", Toast.LENGTH_LONG ).show();
        }
    }

    //Speex声学回音消除器设置布局确定按钮。
    public void OnClickSpeexAecStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //WebRtc定点版声学回音消除器设置布局确定按钮。
    public void OnClickWebRtcAecmStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //WebRtc浮点版声学回音消除器设置布局的删除内存块文件按钮。
    public void OnClickWebRtcAecDelMemFileBtn( View ViewPt )
    {
        String p_WebRtcAecMemFileFullPathStrPt = m_ExternalDirFullAbsPathStrPt + "/WebRtcAecMem";
        File file = new File( p_WebRtcAecMemFileFullPathStrPt );
        if( file.exists() )
        {
            if( file.delete() )
            {
                Toast.makeText( this, "删除WebRtc浮点版声学回音消除器的内存块文件 " + p_WebRtcAecMemFileFullPathStrPt + " 成功。", Toast.LENGTH_LONG ).show();
            }
            else
            {
                Toast.makeText( this, "删除WebRtc浮点版声学回音消除器的内存块文件 " + p_WebRtcAecMemFileFullPathStrPt + " 失败。", Toast.LENGTH_LONG ).show();
            }
        }
        else
        {
            Toast.makeText( this, "WebRtc浮点版声学回音消除器的内存块文件 " + p_WebRtcAecMemFileFullPathStrPt + " 不存在。", Toast.LENGTH_LONG ).show();
        }
    }

    //WebRtc浮点版声学回音消除器设置布局确定按钮。
    public void OnClickWebRtcAecStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //SpeexWebRtc三重声学回音消除器设置布局确定按钮。
    public void OnClickSpeexWebRtcAecStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //Speex预处理器的噪音抑制设置布局确定按钮。
    public void OnClickSpeexPrpocsNsStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //WebRtc定点版噪音抑制器设置布局确定按钮。
    public void OnClickWebRtcNsxStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //WebRtc浮点版噪音抑制器设置布局确定按钮。
    public void OnClickWebRtcNsStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //Speex预处理器的其他功能设置布局确定按钮。
    public void OnClickSpeexPrpocsStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //Speex编解码器设置布局确定按钮。
    public void OnClickSpeexCodecStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //Opus编解码器设置布局确定按钮。
    public void OnOpusCodecSettingOkClick( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //保存音频输入输出到Wave文件设置布局确定按钮。
    public void OnClickSaveAdoInptOtptToWaveFileStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //OpenH264编解码器设置布局确定按钮。
    public void OnClickOpenH264CodecStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }

    //系统自带H264编解码器设置布局确定按钮。
    public void OnClickSystemH264CodecStngOkBtn( View ViewPt )
    {
        setContentView( m_StngLyotViewPt );
        m_CurActivityLyotViewPt = m_StngLyotViewPt;
    }
}
