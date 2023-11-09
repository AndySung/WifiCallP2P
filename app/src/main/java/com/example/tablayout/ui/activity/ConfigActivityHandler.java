package com.example.tablayout.ui.activity;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.tablayout.R;
import com.example.tablayout.voip.FrgndSrvc;

import java.text.SimpleDateFormat;
import java.util.Date;

//主界面消息处理。
public class ConfigActivityHandler extends Handler {
    static String m_CurClsNameStrPt = "ConfigActivityHandler"; //当前类名称字符串的指针。

    ConfigVoIPActivity m_MainActivityPt; //存放主界面的指针。
    ServiceConnection m_FrgndSrvcCnctPt; //存放前台服务连接器的指针。
    AlertDialog m_RqstCnctDlgPt; //存放请求连接对话框的指针。

    public enum Msg {
        MediaPocsThrdInit, //主界面消息：初始化媒体处理线程。
        MediaPocsThrdDstoy, //主界面消息：销毁媒体处理线程。
        RqstCnctDlgInit, //主界面消息：初始化请求连接对话框。
        RqstCnctDlgDstoy, //主界面消息：销毁请求连接对话框。
        PttBtnInit, //主界面消息：初始化一键即按即通按钮。
        PttBtnDstoy, //主界面消息：销毁一键即按即通按钮。
        ShowLog, //主界面消息：显示日志。
        Vibrate, //主界面消息：振动。
    }

    public void handleMessage(Message MessagePt) {
        switch (Msg.values()[MessagePt.what]) {
            case MediaPocsThrdInit: {
                if (m_MainActivityPt.m_MyMediaPocsThrdPt.m_NtwkPt.m_IsCreateSrvrOrClnt == 1) //如果是创建服务端。
                {
                    ((RadioButton) m_MainActivityPt.findViewById(R.id.UseTcpPrtclRdBtnId)).setEnabled(false); //设置TCP协议按钮为不可用。
                    ((RadioButton) m_MainActivityPt.findViewById(R.id.UseUdpPrtclRdBtnId)).setEnabled(false); //设置UDP协议按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.XfrPrtclStngBtnId)).setEnabled(false); //设置传输协议设置按钮为不可用。
                    ((EditText) m_MainActivityPt.findViewById(R.id.IPAddrEdTxtId)).setEnabled(false); //设置IP地址控件为不可用。
                    ((EditText) m_MainActivityPt.findViewById(R.id.PortEdTxtId)).setEnabled(false); //设置端口控件为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.CreateSrvrBtnId)).setText("中断"); //设置创建服务端按钮的内容为“中断”。
                    ((Button) m_MainActivityPt.findViewById(R.id.CnctSrvrBtnId)).setEnabled(false); //设置连接服务端按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.StngBtnId)).setEnabled(false); //设置设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.SaveStngBtnId)).setEnabled(false); //设置保存设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.ReadStngBtnId)).setEnabled(false); //设置读取设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.DelStngBtnId)).setEnabled(false); //设置删除设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.ResetStngBtnId)).setEnabled(false); //设置重置设置按钮为不可用。
                } else //如果是创建客户端。
                {
                    ((RadioButton) m_MainActivityPt.findViewById(R.id.UseTcpPrtclRdBtnId)).setEnabled(false); //设置TCP协议按钮为不可用。
                    ((RadioButton) m_MainActivityPt.findViewById(R.id.UseUdpPrtclRdBtnId)).setEnabled(false); //设置UDP协议按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.XfrPrtclStngBtnId)).setEnabled(false); //设置传输协议设置按钮为不可用。
                    ((EditText) m_MainActivityPt.findViewById(R.id.IPAddrEdTxtId)).setEnabled(false); //设置IP地址控件为不可用。
                    ((EditText) m_MainActivityPt.findViewById(R.id.PortEdTxtId)).setEnabled(false); //设置端口控件为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.CreateSrvrBtnId)).setEnabled(false); //设置创建服务端按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.CnctSrvrBtnId)).setText("中断"); //设置连接服务端按钮的内容为“中断”。
                    ((Button) m_MainActivityPt.findViewById(R.id.StngBtnId)).setEnabled(false); //设置设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.SaveStngBtnId)).setEnabled(false); //设置保存设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.ReadStngBtnId)).setEnabled(false); //设置读取设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.DelStngBtnId)).setEnabled(false); //设置删除设置按钮为不可用。
                    ((Button) m_MainActivityPt.findViewById(R.id.ResetStngBtnId)).setEnabled(false); //设置重置设置按钮为不可用。
                }

                //创建并绑定前台服务，从而确保本进程在转入后台或系统锁屏时不会被系统限制运行，且只能放在主线程中执行，因为要使用界面。
                if (((CheckBox) m_MainActivityPt.m_StngLyotViewPt.findViewById(R.id.IsUseFrgndSrvcCkBoxId)).isChecked() && (m_FrgndSrvcCnctPt == null)) {
                    m_FrgndSrvcCnctPt = new ServiceConnection() //创建存放前台服务连接器。
                    {
                        @Override
                        public void onServiceConnected(ComponentName name, IBinder service) //前台服务绑定成功。
                        {
                            ((FrgndSrvc.FrgndSrvcBinder) service).SetForeground(m_MainActivityPt); //将服务设置为前台服务。
                        }

                        @Override
                        public void onServiceDisconnected(ComponentName name) //前台服务解除绑定。
                        {

                        }
                    };
                    m_MainActivityPt.bindService(new Intent(m_MainActivityPt, FrgndSrvc.class), m_FrgndSrvcCnctPt, Context.BIND_AUTO_CREATE); //创建并绑定前台服务。
                }
                break;
            }
            case MediaPocsThrdDstoy: {
                m_MainActivityPt.m_MyMediaPocsThrdPt = null;

                if (m_FrgndSrvcCnctPt != null) //如果已经创建并绑定了前台服务。
                {
                    m_MainActivityPt.unbindService(m_FrgndSrvcCnctPt); //解除绑定并销毁前台服务。
                    m_FrgndSrvcCnctPt = null;
                }

                ((RadioButton) m_MainActivityPt.findViewById(R.id.UseTcpPrtclRdBtnId)).setEnabled(true); //设置TCP协议按钮为可用。
                ((RadioButton) m_MainActivityPt.findViewById(R.id.UseUdpPrtclRdBtnId)).setEnabled(true); //设置UDP协议按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.XfrPrtclStngBtnId)).setEnabled(true); //设置传输协议设置按钮为不可用。
                ((EditText) m_MainActivityPt.findViewById(R.id.IPAddrEdTxtId)).setEnabled(true); //设置IP地址控件为可用。
                ((EditText) m_MainActivityPt.findViewById(R.id.PortEdTxtId)).setEnabled(true); //设置端口控件为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.CreateSrvrBtnId)).setText("创建服务端"); //设置创建服务端按钮的内容为“创建服务端”。
                ((Button) m_MainActivityPt.findViewById(R.id.CnctSrvrBtnId)).setEnabled(true); //设置连接服务端按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.CnctSrvrBtnId)).setText("连接服务端"); //设置连接服务端按钮的内容为“连接服务端”。
                ((Button) m_MainActivityPt.findViewById(R.id.CreateSrvrBtnId)).setEnabled(true); //设置创建服务端按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.StngBtnId)).setEnabled(true); //设置设置按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.SaveStngBtnId)).setEnabled(true); //设置保存设置按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.ReadStngBtnId)).setEnabled(true); //设置读取设置按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.DelStngBtnId)).setEnabled(true); //设置删除设置按钮为可用。
                ((Button) m_MainActivityPt.findViewById(R.id.ResetStngBtnId)).setEnabled(true); //设置重置设置按钮为可用。
                break;
            }
            case PttBtnInit: {
                ((Button) m_MainActivityPt.findViewById(R.id.PttBtnId)).setVisibility(Button.VISIBLE); //设置一键即按即通按钮为可见。
                break;
            }
            case PttBtnDstoy: {
                ((Button) m_MainActivityPt.findViewById(R.id.PttBtnId)).setVisibility(Button.INVISIBLE); //设置一键即按即通按钮为不可见。
                break;
            }
            case RqstCnctDlgInit: {
                AlertDialog.Builder builder = new AlertDialog.Builder(m_MainActivityPt);

                builder.setCancelable(false); //点击对话框以外的区域是否让对话框消失
                builder.setTitle(R.string.app_name);

                if (m_MainActivityPt.m_MyMediaPocsThrdPt.m_NtwkPt.m_IsCreateSrvrOrClnt == 1) //如果是创建服务端。
                {
                    builder.setMessage("您是否允许远端[" + MessagePt.obj + "]的连接？");

                    //设置正面按钮。
                    builder.setPositiveButton("允许", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_MainActivityPt.m_MyMediaPocsThrdPt.m_RqstCnctRslt = 1;
                            m_RqstCnctDlgPt = null;
                        }
                    });
                    //设置反面按钮。
                    builder.setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_MainActivityPt.m_MyMediaPocsThrdPt.m_RqstCnctRslt = 2;
                            m_RqstCnctDlgPt = null;
                        }
                    });
                } else //如果是创建客户端。
                {
                    builder.setMessage("等待远端[" + MessagePt.obj + "]允许您的连接...");

                    //设置反面按钮。
                    builder.setNegativeButton("中断", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            m_MainActivityPt.m_MyMediaPocsThrdPt.m_RqstCnctRslt = 2;
                            m_RqstCnctDlgPt = null;
                        }
                    });
                }

                m_RqstCnctDlgPt = builder.create(); //创建AlertDialog对象。
                m_RqstCnctDlgPt.show();
                break;
            }
            case RqstCnctDlgDstoy: {
                if (m_RqstCnctDlgPt != null) {
                    m_RqstCnctDlgPt.cancel();
                    m_RqstCnctDlgPt = null;
                }
                break;
            }
            case ShowLog: {
                TextView p_LogTextView = new TextView(m_MainActivityPt);
                p_LogTextView.setText((new SimpleDateFormat("HH:mm:ss SSS")).format(new Date()) + "：" + MessagePt.obj);
                ((LinearLayout) m_MainActivityPt.m_MainLyotViewPt.findViewById(R.id.LogLinearLyotId)).addView(p_LogTextView);
                break;
            }
            case Vibrate: {
                ((Vibrator) m_MainActivityPt.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(50);
                break;
            }
        }
    }
}
