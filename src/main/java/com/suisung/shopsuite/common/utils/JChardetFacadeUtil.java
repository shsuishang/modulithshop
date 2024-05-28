package com.suisung.shopsuite.common.utils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;

/**
 * 文件编码检测工具, 核心代码来自 cpDetector 和 jChardet, 可以检测大多数文件的编码
 *
 * @author Xinze
 * @since 2019-09-15 09:24:20
 */
public class JChardetFacadeUtil {

    public static String detectCodepage(URL url) {
        try {
            Charset ret = JChardetFacade.getInstance().detectCodepage(url);
            return ret == null ? null : ret.name();
        } catch (Exception ignored) {
        }
        return null;
    }

    /**
     * 下面代码来自: https://github.com/r91987/cpdetector
     */
    public static class JChardetFacade extends AbstractCodepageDetector implements nsICharsetDetectionObserver {
        private static JChardetFacade instance = null;
        private static nsDetector det;
        private final byte[] buf = new byte[4096];
        private Charset codpage = null;
        private boolean m_guessing = true;
        private int amountOfVerifiers = 0;

        private JChardetFacade() {
            det = new nsDetector(0);
            det.Init(this);
            this.amountOfVerifiers = det.getProbableCharsets().length;
        }

        public static JChardetFacade getInstance() {
            if (instance == null) {
                instance = new JChardetFacade();
            }

            return instance;
        }

        public synchronized Charset detectCodepage(InputStream in, int length) throws IOException {
            this.Reset();
            int read = 0;
            boolean done = false;
            boolean isAscii = true;
            Charset ret = null;

            int len;
            do {
                len = in.read(this.buf, 0, Math.min(this.buf.length, length - read));
                if (len > 0) {
                    read += len;
                }

                if (!done) {
                    done = det.DoIt(this.buf, len, false);
                }
            } while (len > 0 && !done);

            det.DataEnd();
            if (this.codpage == null) {
                if (this.m_guessing) {
                    ret = this.guess();
                }
            } else {
                ret = this.codpage;
            }
            return ret;
        }

        private Charset guess() {
            Charset ret = null;
            String[] possibilities = det.getProbableCharsets();
            if (possibilities.length == this.amountOfVerifiers) {
                ret = StandardCharsets.US_ASCII;
            } else {
                String check = possibilities[0];
                if (!check.equalsIgnoreCase("nomatch")) {
                    for (int i = 0; ret == null && i < possibilities.length; ++i) {
                        try {
                            ret = Charset.forName(possibilities[i]);
                        } catch (UnsupportedCharsetException ignored) {
                        }
                    }
                }
            }
            return ret;
        }

        public void Notify(String charset) {
            this.codpage = Charset.forName(charset);
        }

        public void Reset() {
            det.Reset();
            this.codpage = null;
        }

        public boolean isGuessing() {
            return this.m_guessing;
        }

        public synchronized void setGuessing(boolean guessing) {
            this.m_guessing = guessing;
        }
    }

    /**
     *
     */
    public static abstract class AbstractCodepageDetector implements ICodepageDetector {
        public AbstractCodepageDetector() {
        }

        public Charset detectCodepage(URL url) throws IOException {
            BufferedInputStream in = new BufferedInputStream(url.openStream());
            Charset result = this.detectCodepage(in, 2147483647);
            in.close();
            return result;
        }

        public final Reader open(URL url) throws IOException {
            Reader ret = null;
            Charset cs = this.detectCodepage(url);
            if (cs != null) {
                ret = new InputStreamReader(new BufferedInputStream(url.openStream()), cs);
            }

            return ret;
        }

        public int compareTo(Object o) {
            String other = o.getClass().getName();
            String mine = this.getClass().getName();
            return mine.compareTo(other);
        }
    }

    /**
     *
     */
    interface ICodepageDetector extends Serializable, Comparable {
        Reader open(URL var1) throws IOException;

        Charset detectCodepage(URL var1) throws IOException;

        Charset detectCodepage(InputStream var1, int var2) throws IOException;
    }

    /**
     * 以下代码开始是由Mozilla组织提供的JChardet, 它可以检测大多数文件的编码
     * http://jchardet.sourceforge.net/
     */
    public static class nsDetector extends nsPSMDetector implements nsICharsetDetector {
        nsICharsetDetectionObserver mObserver = null;

        public nsDetector() {
        }

        public nsDetector(int var1) {
            super(var1);
        }

        public void Init(nsICharsetDetectionObserver var1) {
            this.mObserver = var1;
        }

        public boolean DoIt(byte[] var1, int var2, boolean var3) {
            if (var1 != null && !var3) {
                this.HandleData(var1, var2);
                return this.mDone;
            } else {
                return false;
            }
        }

        public void Done() {
            this.DataEnd();
        }

        public void Report(String var1) {
            if (this.mObserver != null) {
                this.mObserver.Notify(var1);
            }

        }

        public boolean isAscii(byte[] var1, int var2) {
            for (int var3 = 0; var3 < var2; ++var3) {
                if ((128 & var1[var3]) != 0) {
                    return false;
                }
            }

            return true;
        }
    }

    /**
     *
     */
    public static abstract class nsPSMDetector {
        public static final int ALL = 0;
        public static final int JAPANESE = 1;
        public static final int CHINESE = 2;
        public static final int SIMPLIFIED_CHINESE = 3;
        public static final int TRADITIONAL_CHINESE = 4;
        public static final int KOREAN = 5;
        public static final int NO_OF_LANGUAGES = 6;
        public static final int MAX_VERIFIERS = 16;
        nsVerifier[] mVerifier;
        nsEUCStatistics[] mStatisticsData;
        nsEUCSampler mSampler = new nsEUCSampler();
        byte[] mState = new byte[16];
        int[] mItemIdx = new int[16];
        int mItems;
        int mClassItems;
        boolean mDone;
        boolean mRunSampler;
        boolean mClassRunSampler;

        public nsPSMDetector() {
            this.initVerifiers(0);
            this.Reset();
        }

        public nsPSMDetector(int var1) {
            this.initVerifiers(var1);
            this.Reset();
        }

        public nsPSMDetector(int var1, nsVerifier[] var2, nsEUCStatistics[] var3) {
            this.mClassRunSampler = var3 != null;
            this.mStatisticsData = var3;
            this.mVerifier = var2;
            this.mClassItems = var1;
            this.Reset();
        }

        public void Reset() {
            this.mRunSampler = this.mClassRunSampler;
            this.mDone = false;
            this.mItems = this.mClassItems;

            for (int var1 = 0; var1 < this.mItems; this.mItemIdx[var1] = var1++) {
                this.mState[var1] = 0;
            }

            this.mSampler.Reset();
        }

        protected void initVerifiers(int var1) {
            boolean var2 = false;
            int var3;
            if (var1 >= 0 && var1 < 6) {
                var3 = var1;
            } else {
                var3 = 0;
            }

            this.mVerifier = null;
            this.mStatisticsData = null;
            if (var3 == 4) {
                this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsBIG5Verifier(), new nsISO2022CNVerifier(), new nsEUCTWVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
                this.mStatisticsData = new nsEUCStatistics[]{null, new Big5Statistics(), null, new EUCTWStatistics(), null, null, null};
            } else if (var3 == 5) {
                this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsEUCKRVerifier(), new nsISO2022KRVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
            } else if (var3 == 3) {
                this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsGB2312Verifier(), new nsGB18030Verifier(), new nsISO2022CNVerifier(), new nsHZVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
            } else if (var3 == 1) {
                this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsSJISVerifier(), new nsEUCJPVerifier(), new nsISO2022JPVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
            } else if (var3 == 2) {
                this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsGB2312Verifier(), new nsGB18030Verifier(), new nsBIG5Verifier(), new nsISO2022CNVerifier(), new nsHZVerifier(), new nsEUCTWVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
                this.mStatisticsData = new nsEUCStatistics[]{null, new GB2312Statistics(), null, new Big5Statistics(), null, null, new EUCTWStatistics(), null, null, null};
            } else if (var3 == 0) {
                this.mVerifier = new nsVerifier[]{new nsUTF8Verifier(), new nsSJISVerifier(), new nsEUCJPVerifier(), new nsISO2022JPVerifier(), new nsEUCKRVerifier(), new nsISO2022KRVerifier(), new nsBIG5Verifier(), new nsEUCTWVerifier(), new nsGB2312Verifier(), new nsGB18030Verifier(), new nsISO2022CNVerifier(), new nsHZVerifier(), new nsCP1252Verifier(), new nsUCS2BEVerifier(), new nsUCS2LEVerifier()};
                this.mStatisticsData = new nsEUCStatistics[]{null, null, new EUCJPStatistics(), null, new EUCKRStatistics(), null, new Big5Statistics(), new EUCTWStatistics(), new GB2312Statistics(), null, null, null, null, null, null};
            }

            this.mClassRunSampler = this.mStatisticsData != null;
            this.mClassItems = this.mVerifier.length;
        }

        public abstract void Report(String var1);

        public boolean HandleData(byte[] var1, int var2) {
            for (int var3 = 0; var3 < var2; ++var3) {
                byte var5 = var1[var3];
                int var4 = 0;

                while (var4 < this.mItems) {
                    byte var6 = nsVerifier.getNextState(this.mVerifier[this.mItemIdx[var4]], var5, this.mState[var4]);
                    if (var6 == 2) {
                        this.Report(this.mVerifier[this.mItemIdx[var4]].charset());
                        this.mDone = true;
                        return this.mDone;
                    }

                    if (var6 == 1) {
                        --this.mItems;
                        if (var4 < this.mItems) {
                            this.mItemIdx[var4] = this.mItemIdx[this.mItems];
                            this.mState[var4] = this.mState[this.mItems];
                        }
                    } else {
                        this.mState[var4++] = var6;
                    }
                }

                if (this.mItems <= 1) {
                    if (1 == this.mItems) {
                        this.Report(this.mVerifier[this.mItemIdx[0]].charset());
                    }

                    this.mDone = true;
                    return this.mDone;
                }

                int var7 = 0;
                int var8 = 0;

                for (var4 = 0; var4 < this.mItems; ++var4) {
                    if (!this.mVerifier[this.mItemIdx[var4]].isUCS2() && !this.mVerifier[this.mItemIdx[var4]].isUCS2()) {
                        ++var7;
                        var8 = var4;
                    }
                }

                if (1 == var7) {
                    this.Report(this.mVerifier[this.mItemIdx[var8]].charset());
                    this.mDone = true;
                    return this.mDone;
                }
            }

            if (this.mRunSampler) {
                this.Sample(var1, var2);
            }

            return this.mDone;
        }

        public void DataEnd() {
            if (!this.mDone) {
                if (this.mItems == 2) {
                    if (this.mVerifier[this.mItemIdx[0]].charset().equals("GB18030")) {
                        this.Report(this.mVerifier[this.mItemIdx[1]].charset());
                        this.mDone = true;
                    } else if (this.mVerifier[this.mItemIdx[1]].charset().equals("GB18030")) {
                        this.Report(this.mVerifier[this.mItemIdx[0]].charset());
                        this.mDone = true;
                    }
                }

                if (this.mRunSampler) {
                    this.Sample(null, 0, true);
                }

            }
        }

        public void Sample(byte[] var1, int var2) {
            this.Sample(var1, var2, false);
        }

        public void Sample(byte[] var1, int var2, boolean var3) {
            int var4 = 0;
            int var6 = 0;

            int var5;
            for (var5 = 0; var5 < this.mItems; ++var5) {
                if (null != this.mStatisticsData[this.mItemIdx[var5]]) {
                    ++var6;
                }

                if (!this.mVerifier[this.mItemIdx[var5]].isUCS2() && !this.mVerifier[this.mItemIdx[var5]].charset().equals("GB18030")) {
                    ++var4;
                }
            }

            this.mRunSampler = var6 > 1;
            if (this.mRunSampler) {
                this.mRunSampler = this.mSampler.Sample(var1, var2);
                if ((var3 && this.mSampler.GetSomeData() || this.mSampler.EnoughData()) && var6 == var4) {
                    this.mSampler.CalFreq();
                    int var7 = -1;
                    int var8 = 0;
                    float var9 = 0.0F;

                    for (var5 = 0; var5 < this.mItems; ++var5) {
                        if (null != this.mStatisticsData[this.mItemIdx[var5]] && !this.mVerifier[this.mItemIdx[var5]].charset().equals("Big5")) {
                            float var10 = this.mSampler.GetScore(this.mStatisticsData[this.mItemIdx[var5]].mFirstByteFreq(), this.mStatisticsData[this.mItemIdx[var5]].mFirstByteWeight(), this.mStatisticsData[this.mItemIdx[var5]].mSecondByteFreq(), this.mStatisticsData[this.mItemIdx[var5]].mSecondByteWeight());
                            if (0 == var8++ || var9 > var10) {
                                var9 = var10;
                                var7 = var5;
                            }
                        }
                    }

                    if (var7 >= 0) {
                        this.Report(this.mVerifier[this.mItemIdx[var7]].charset());
                        this.mDone = true;
                    }
                }
            }

        }

        public String[] getProbableCharsets() {
            String[] var1;
            if (this.mItems <= 0) {
                var1 = new String[]{"nomatch"};
                return var1;
            } else {
                var1 = new String[this.mItems];

                for (int var2 = 0; var2 < this.mItems; ++var2) {
                    var1[var2] = this.mVerifier[this.mItemIdx[var2]].charset();
                }

                return var1;
            }
        }
    }

    /**
     *
     */
    public interface nsICharsetDetectionObserver {
        void Notify(String var1);
    }

    /**
     *
     */
    public interface nsICharsetDetector {
        void Init(nsICharsetDetectionObserver var1);

        boolean DoIt(byte[] var1, int var2, boolean var3);

        void Done();
    }

    /**
     *
     */
    public static abstract class nsVerifier {
        static final byte eStart = 0;
        static final byte eError = 1;
        static final byte eItsMe = 2;
        static final int eidxSft4bits = 3;
        static final int eSftMsk4bits = 7;
        static final int eBitSft4bits = 2;
        static final int eUnitMsk4bits = 15;

        nsVerifier() {
        }

        public abstract String charset();

        public abstract int stFactor();

        public abstract int[] cclass();

        public abstract int[] states();

        public abstract boolean isUCS2();

        public static byte getNextState(nsVerifier var0, byte var1, byte var2) {
            return (byte) (255 & var0.states()[(var2 * var0.stFactor() + (var0.cclass()[(var1 & 255) >> 3] >> ((var1 & 7) << 2) & 15) & 255) >> 3] >> ((var2 * var0.stFactor() + (var0.cclass()[(var1 & 255) >> 3] >> ((var1 & 7) << 2) & 15) & 255 & 7) << 2) & 15);
        }
    }

    /**
     *
     */
    public static class nsEUCSampler {
        int mTotal = 0;
        int mThreshold = 200;
        int mState = 0;
        public int[] mFirstByteCnt = new int[94];
        public int[] mSecondByteCnt = new int[94];
        public float[] mFirstByteFreq = new float[94];
        public float[] mSecondByteFreq = new float[94];

        public nsEUCSampler() {
            this.Reset();
        }

        public void Reset() {
            this.mTotal = 0;
            this.mState = 0;

            for (int var1 = 0; var1 < 94; ++var1) {
                this.mFirstByteCnt[var1] = this.mSecondByteCnt[var1] = 0;
            }

        }

        boolean EnoughData() {
            return this.mTotal > this.mThreshold;
        }

        boolean GetSomeData() {
            return this.mTotal > 1;
        }

        boolean Sample(byte[] var1, int var2) {
            if (this.mState == 1) {
                return false;
            } else {
                int var3 = 0;

                for (int var4 = 0; var4 < var2 && 1 != this.mState; ++var3) {
                    int var10002;
                    switch (this.mState) {
                        case 0:
                            if ((var1[var3] & 128) != 0) {
                                if (255 != (255 & var1[var3]) && 161 <= (255 & var1[var3])) {
                                    ++this.mTotal;
                                    var10002 = this.mFirstByteCnt[(255 & var1[var3]) - 161]++;
                                    this.mState = 2;
                                } else {
                                    this.mState = 1;
                                }
                            }
                        case 1:
                            break;
                        case 2:
                            if ((var1[var3] & 128) != 0) {
                                if (255 != (255 & var1[var3]) && 161 <= (255 & var1[var3])) {
                                    ++this.mTotal;
                                    var10002 = this.mSecondByteCnt[(255 & var1[var3]) - 161]++;
                                    this.mState = 0;
                                } else {
                                    this.mState = 1;
                                }
                            } else {
                                this.mState = 1;
                            }
                            break;
                        default:
                            this.mState = 1;
                    }

                    ++var4;
                }

                return 1 != this.mState;
            }
        }

        void CalFreq() {
            for (int var1 = 0; var1 < 94; ++var1) {
                this.mFirstByteFreq[var1] = (float) this.mFirstByteCnt[var1] / (float) this.mTotal;
                this.mSecondByteFreq[var1] = (float) this.mSecondByteCnt[var1] / (float) this.mTotal;
            }

        }

        float GetScore(float[] var1, float var2, float[] var3, float var4) {
            return var2 * this.GetScore(var1, this.mFirstByteFreq) + var4 * this.GetScore(var3, this.mSecondByteFreq);
        }

        float GetScore(float[] var1, float[] var2) {
            float var4 = 0.0F;

            for (int var5 = 0; var5 < 94; ++var5) {
                float var3 = var1[var5] - var2[var5];
                var4 += var3 * var3;
            }

            return (float) Math.sqrt(var4) / 94.0F;
        }
    }

    /**
     *
     */
    public static abstract class nsEUCStatistics {
        public abstract float[] mFirstByteFreq();

        public abstract float mFirstByteStdDev();

        public abstract float mFirstByteMean();

        public abstract float mFirstByteWeight();

        public abstract float[] mSecondByteFreq();

        public abstract float mSecondByteStdDev();

        public abstract float mSecondByteMean();

        public abstract float mSecondByteWeight();

        public nsEUCStatistics() {
        }
    }

    /**
     *
     */
    public static class EUCJPStatistics extends nsEUCStatistics {
        static float[] mFirstByteFreq;
        static float mFirstByteStdDev;
        static float mFirstByteMean;
        static float mFirstByteWeight;
        static float[] mSecondByteFreq;
        static float mSecondByteStdDev;
        static float mSecondByteMean;
        static float mSecondByteWeight;

        public float[] mFirstByteFreq() {
            return mFirstByteFreq;
        }

        public float mFirstByteStdDev() {
            return mFirstByteStdDev;
        }

        public float mFirstByteMean() {
            return mFirstByteMean;
        }

        public float mFirstByteWeight() {
            return mFirstByteWeight;
        }

        public float[] mSecondByteFreq() {
            return mSecondByteFreq;
        }

        public float mSecondByteStdDev() {
            return mSecondByteStdDev;
        }

        public float mSecondByteMean() {
            return mSecondByteMean;
        }

        public float mSecondByteWeight() {
            return mSecondByteWeight;
        }

        public EUCJPStatistics() {
            mFirstByteFreq = new float[]{0.364808F, 0.0F, 0.0F, 0.145325F, 0.304891F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.001835F, 0.010771F, 0.006462F, 0.001157F, 0.002114F, 0.003231F, 0.001356F, 0.00742F, 0.004189F, 0.003231F, 0.003032F, 0.03319F, 0.006303F, 0.006064F, 0.009973F, 0.002354F, 0.00367F, 0.009135F, 0.001675F, 0.002792F, 0.002194F, 0.01472F, 0.011928F, 8.78E-4F, 0.013124F, 0.001077F, 0.009295F, 0.003471F, 0.002872F, 0.002433F, 9.57E-4F, 0.001636F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0E-5F, 2.79E-4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0E-5F, 0.0F};
            mFirstByteStdDev = 0.050407F;
            mFirstByteMean = 0.010638F;
            mFirstByteWeight = 0.640871F;
            mSecondByteFreq = new float[]{0.002473F, 0.039134F, 0.152745F, 0.009694F, 3.59E-4F, 0.02218F, 7.58E-4F, 0.004308F, 1.6E-4F, 0.002513F, 0.003072F, 0.001316F, 0.00383F, 0.001037F, 0.00359F, 9.57E-4F, 1.6E-4F, 2.39E-4F, 0.006462F, 0.001596F, 0.031554F, 0.001316F, 0.002194F, 0.016555F, 0.003271F, 6.78E-4F, 5.98E-4F, 0.206438F, 7.18E-4F, 0.001077F, 0.00371F, 0.001356F, 0.001356F, 4.39E-4F, 0.004388F, 0.005704F, 8.78E-4F, 0.010172F, 0.007061F, 0.01468F, 6.38E-4F, 0.02573F, 0.002792F, 7.18E-4F, 0.001795F, 0.091551F, 7.58E-4F, 0.003909F, 5.58E-4F, 0.031195F, 0.007061F, 0.001316F, 0.022579F, 0.006981F, 0.00726F, 0.001117F, 2.39E-4F, 0.012127F, 8.78E-4F, 0.00379F, 0.001077F, 7.58E-4F, 0.002114F, 0.002234F, 6.78E-4F, 0.002992F, 0.003311F, 0.023416F, 0.001237F, 0.002753F, 0.005146F, 0.002194F, 0.007021F, 0.008497F, 0.013763F, 0.011768F, 0.006303F, 0.001915F, 6.38E-4F, 0.008776F, 9.18E-4F, 0.003431F, 0.057603F, 4.39E-4F, 4.39E-4F, 7.58E-4F, 0.002872F, 0.001675F, 0.01105F, 0.0F, 2.79E-4F, 0.012127F, 7.18E-4F, 0.00738F};
            mSecondByteStdDev = 0.028247F;
            mSecondByteMean = 0.010638F;
            mSecondByteWeight = 0.359129F;
        }
    }

    /**
     *
     */
    public static class nsEUCJPVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsEUCJPVerifier() {
            cclass = new int[32];
            cclass[0] = 1145324612;
            cclass[1] = 1430537284;
            cclass[2] = 1145324612;
            cclass[3] = 1145328708;
            cclass[4] = 1145324612;
            cclass[5] = 1145324612;
            cclass[6] = 1145324612;
            cclass[7] = 1145324612;
            cclass[8] = 1145324612;
            cclass[9] = 1145324612;
            cclass[10] = 1145324612;
            cclass[11] = 1145324612;
            cclass[12] = 1145324612;
            cclass[13] = 1145324612;
            cclass[14] = 1145324612;
            cclass[15] = 1145324612;
            cclass[16] = 1431655765;
            cclass[17] = 827675989;
            cclass[18] = 1431655765;
            cclass[19] = 1431655765;
            cclass[20] = 572662309;
            cclass[21] = 572662306;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662306;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 0;
            cclass[29] = 0;
            cclass[30] = 0;
            cclass[31] = 1342177280;
            states = new int[5];
            states[0] = 286282563;
            states[1] = 572657937;
            states[2] = 286265378;
            states[3] = 319885329;
            states[4] = 4371;
            charset = "EUC-JP";
            stFactor = 6;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class EUCKRStatistics extends nsEUCStatistics {
        static float[] mFirstByteFreq;
        static float mFirstByteStdDev;
        static float mFirstByteMean;
        static float mFirstByteWeight;
        static float[] mSecondByteFreq;
        static float mSecondByteStdDev;
        static float mSecondByteMean;
        static float mSecondByteWeight;

        public float[] mFirstByteFreq() {
            return mFirstByteFreq;
        }

        public float mFirstByteStdDev() {
            return mFirstByteStdDev;
        }

        public float mFirstByteMean() {
            return mFirstByteMean;
        }

        public float mFirstByteWeight() {
            return mFirstByteWeight;
        }

        public float[] mSecondByteFreq() {
            return mSecondByteFreq;
        }

        public float mSecondByteStdDev() {
            return mSecondByteStdDev;
        }

        public float mSecondByteMean() {
            return mSecondByteMean;
        }

        public float mSecondByteWeight() {
            return mSecondByteWeight;
        }

        public EUCKRStatistics() {
            mFirstByteFreq = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 4.12E-4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.057502F, 0.033182F, 0.002267F, 0.016076F, 0.014633F, 0.032976F, 0.004122F, 0.011336F, 0.058533F, 0.024526F, 0.025969F, 0.054411F, 0.01958F, 0.063273F, 0.113974F, 0.029885F, 0.150041F, 0.059151F, 0.002679F, 0.009893F, 0.014839F, 0.026381F, 0.015045F, 0.069456F, 0.08986F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
            mFirstByteStdDev = 0.025593F;
            mFirstByteMean = 0.010638F;
            mFirstByteWeight = 0.647437F;
            mSecondByteFreq = new float[]{0.016694F, 0.0F, 0.012778F, 0.030091F, 0.002679F, 0.006595F, 0.001855F, 8.24E-4F, 0.005977F, 0.00474F, 0.003092F, 8.24E-4F, 0.01958F, 0.037304F, 0.008244F, 0.014633F, 0.001031F, 0.0F, 0.003298F, 0.002061F, 0.006183F, 0.005977F, 8.24E-4F, 0.021847F, 0.014839F, 0.052968F, 0.017312F, 0.007626F, 4.12E-4F, 8.24E-4F, 0.011129F, 0.0F, 4.12E-4F, 0.001649F, 0.005977F, 0.065746F, 0.020198F, 0.021434F, 0.014633F, 0.004122F, 0.001649F, 8.24E-4F, 8.24E-4F, 0.051937F, 0.01958F, 0.023289F, 0.026381F, 0.040396F, 0.009068F, 0.001443F, 0.00371F, 0.00742F, 0.001443F, 0.01319F, 0.002885F, 4.12E-4F, 0.003298F, 0.025969F, 4.12E-4F, 4.12E-4F, 0.006183F, 0.003298F, 0.066983F, 0.002679F, 0.002267F, 0.011129F, 4.12E-4F, 0.010099F, 0.015251F, 0.007626F, 0.043899F, 0.00371F, 0.002679F, 0.001443F, 0.010923F, 0.002885F, 0.009068F, 0.019992F, 4.12E-4F, 0.00845F, 0.005153F, 0.0F, 0.010099F, 0.0F, 0.001649F, 0.01216F, 0.011542F, 0.006595F, 0.001855F, 0.010923F, 4.12E-4F, 0.023702F, 0.00371F, 0.001855F};
            mSecondByteStdDev = 0.013937F;
            mSecondByteMean = 0.010638F;
            mSecondByteWeight = 0.352563F;
        }
    }

    /**
     *
     */
    public static class nsEUCKRVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsEUCKRVerifier() {
            cclass = new int[32];
            cclass[0] = 286331153;
            cclass[1] = 1118481;
            cclass[2] = 286331153;
            cclass[3] = 286327057;
            cclass[4] = 286331153;
            cclass[5] = 286331153;
            cclass[6] = 286331153;
            cclass[7] = 286331153;
            cclass[8] = 286331153;
            cclass[9] = 286331153;
            cclass[10] = 286331153;
            cclass[11] = 286331153;
            cclass[12] = 286331153;
            cclass[13] = 286331153;
            cclass[14] = 286331153;
            cclass[15] = 286331153;
            cclass[16] = 0;
            cclass[17] = 0;
            cclass[18] = 0;
            cclass[19] = 0;
            cclass[20] = 572662304;
            cclass[21] = 858923554;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662322;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 572662306;
            cclass[29] = 572662306;
            cclass[30] = 572662306;
            cclass[31] = 35791394;
            states = new int[2];
            states[0] = 286331649;
            states[1] = 1122850;
            charset = "EUC-KR";
            stFactor = 4;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class EUCTWStatistics extends nsEUCStatistics {
        static float[] mFirstByteFreq;
        static float mFirstByteStdDev;
        static float mFirstByteMean;
        static float mFirstByteWeight;
        static float[] mSecondByteFreq;
        static float mSecondByteStdDev;
        static float mSecondByteMean;
        static float mSecondByteWeight;

        public float[] mFirstByteFreq() {
            return mFirstByteFreq;
        }

        public float mFirstByteStdDev() {
            return mFirstByteStdDev;
        }

        public float mFirstByteMean() {
            return mFirstByteMean;
        }

        public float mFirstByteWeight() {
            return mFirstByteWeight;
        }

        public float[] mSecondByteFreq() {
            return mSecondByteFreq;
        }

        public float mSecondByteStdDev() {
            return mSecondByteStdDev;
        }

        public float mSecondByteMean() {
            return mSecondByteMean;
        }

        public float mSecondByteWeight() {
            return mSecondByteWeight;
        }

        public EUCTWStatistics() {
            mFirstByteFreq = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.119286F, 0.052233F, 0.044126F, 0.052494F, 0.045906F, 0.019038F, 0.032465F, 0.026252F, 0.025502F, 0.015963F, 0.052493F, 0.019256F, 0.015137F, 0.031782F, 0.01737F, 0.018494F, 0.015575F, 0.016621F, 0.007444F, 0.011642F, 0.013916F, 0.019159F, 0.016445F, 0.007851F, 0.011079F, 0.022842F, 0.015513F, 0.010033F, 0.00995F, 0.010347F, 0.013103F, 0.015371F, 0.012502F, 0.007436F, 0.018253F, 0.014134F, 0.008907F, 0.005411F, 0.00957F, 0.013598F, 0.006092F, 0.007409F, 0.008432F, 0.005816F, 0.009349F, 0.005472F, 0.00717F, 0.00742F, 0.003681F, 0.007523F, 0.00461F, 0.006154F, 0.003348F, 0.005074F, 0.005922F, 0.005254F, 0.004682F, 0.002093F, 0.0F};
            mFirstByteStdDev = 0.016681F;
            mFirstByteMean = 0.010638F;
            mFirstByteWeight = 0.715599F;
            mSecondByteFreq = new float[]{0.028933F, 0.011371F, 0.011053F, 0.007232F, 0.010192F, 0.004093F, 0.015043F, 0.011752F, 0.022387F, 0.00841F, 0.012448F, 0.007473F, 0.003594F, 0.007139F, 0.018912F, 0.006083F, 0.003302F, 0.010215F, 0.008791F, 0.024236F, 0.014107F, 0.014108F, 0.010303F, 0.009728F, 0.007877F, 0.009719F, 0.007952F, 0.021028F, 0.005764F, 0.009341F, 0.006591F, 0.012517F, 0.005921F, 0.008982F, 0.008771F, 0.012802F, 0.005926F, 0.008342F, 0.003086F, 0.006843F, 0.007576F, 0.004734F, 0.016404F, 0.008803F, 0.008071F, 0.005349F, 0.008566F, 0.01084F, 0.015401F, 0.031904F, 0.00867F, 0.011479F, 0.010936F, 0.007617F, 0.008995F, 0.008114F, 0.008658F, 0.005934F, 0.010452F, 0.009142F, 0.004519F, 0.008339F, 0.007476F, 0.007027F, 0.006025F, 0.021804F, 0.024248F, 0.015895F, 0.003768F, 0.010171F, 0.010007F, 0.010178F, 0.008316F, 0.006832F, 0.006364F, 0.009141F, 0.009148F, 0.012081F, 0.011914F, 0.004464F, 0.014257F, 0.006907F, 0.011292F, 0.018622F, 0.008149F, 0.004636F, 0.006612F, 0.013478F, 0.012614F, 0.005186F, 0.048285F, 0.006816F, 0.006743F, 0.008671F};
            mSecondByteStdDev = 0.00663F;
            mSecondByteMean = 0.010638F;
            mSecondByteWeight = 0.284401F;
        }
    }

    /**
     *
     */
    public static class nsEUCTWVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsEUCTWVerifier() {
            cclass = new int[32];
            cclass[0] = 572662306;
            cclass[1] = 2236962;
            cclass[2] = 572662306;
            cclass[3] = 572654114;
            cclass[4] = 572662306;
            cclass[5] = 572662306;
            cclass[6] = 572662306;
            cclass[7] = 572662306;
            cclass[8] = 572662306;
            cclass[9] = 572662306;
            cclass[10] = 572662306;
            cclass[11] = 572662306;
            cclass[12] = 572662306;
            cclass[13] = 572662306;
            cclass[14] = 572662306;
            cclass[15] = 572662306;
            cclass[16] = 0;
            cclass[17] = 100663296;
            cclass[18] = 0;
            cclass[19] = 0;
            cclass[20] = 1145324592;
            cclass[21] = 286331221;
            cclass[22] = 286331153;
            cclass[23] = 286331153;
            cclass[24] = 858985233;
            cclass[25] = 858993459;
            cclass[26] = 858993459;
            cclass[27] = 858993459;
            cclass[28] = 858993459;
            cclass[29] = 858993459;
            cclass[30] = 858993459;
            cclass[31] = 53687091;
            states = new int[6];
            states[0] = 338898961;
            states[1] = 571543825;
            states[2] = 269623842;
            states[3] = 286330880;
            states[4] = 1052949;
            states[5] = 16;
            charset = "x-euc-tw";
            stFactor = 7;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class Big5Statistics extends nsEUCStatistics {
        static float[] mFirstByteFreq;
        static float mFirstByteStdDev;
        static float mFirstByteMean;
        static float mFirstByteWeight;
        static float[] mSecondByteFreq;
        static float mSecondByteStdDev;
        static float mSecondByteMean;
        static float mSecondByteWeight;

        public float[] mFirstByteFreq() {
            return mFirstByteFreq;
        }

        public float mFirstByteStdDev() {
            return mFirstByteStdDev;
        }

        public float mFirstByteMean() {
            return mFirstByteMean;
        }

        public float mFirstByteWeight() {
            return mFirstByteWeight;
        }

        public float[] mSecondByteFreq() {
            return mSecondByteFreq;
        }

        public float mSecondByteStdDev() {
            return mSecondByteStdDev;
        }

        public float mSecondByteMean() {
            return mSecondByteMean;
        }

        public float mSecondByteWeight() {
            return mSecondByteWeight;
        }

        public Big5Statistics() {
            mFirstByteFreq = new float[]{0.0F, 0.0F, 0.0F, 0.114427F, 0.061058F, 0.075598F, 0.048386F, 0.063966F, 0.027094F, 0.095787F, 0.029525F, 0.031331F, 0.036915F, 0.021805F, 0.019349F, 0.037496F, 0.018068F, 0.01276F, 0.030053F, 0.017339F, 0.016731F, 0.019501F, 0.01124F, 0.032973F, 0.016658F, 0.015872F, 0.021458F, 0.012378F, 0.017003F, 0.020802F, 0.012454F, 0.009239F, 0.012829F, 0.007922F, 0.010079F, 0.009815F, 0.010104F, 0.0F, 0.0F, 0.0F, 5.3E-5F, 3.5E-5F, 1.05E-4F, 3.1E-5F, 8.8E-5F, 2.7E-5F, 2.7E-5F, 2.6E-5F, 3.5E-5F, 2.4E-5F, 3.4E-5F, 3.75E-4F, 2.5E-5F, 2.8E-5F, 2.0E-5F, 2.4E-5F, 2.8E-5F, 3.1E-5F, 5.9E-5F, 4.0E-5F, 3.0E-5F, 7.9E-5F, 3.7E-5F, 4.0E-5F, 2.3E-5F, 3.0E-5F, 2.7E-5F, 6.4E-5F, 2.0E-5F, 2.7E-5F, 2.5E-5F, 7.4E-5F, 1.9E-5F, 2.3E-5F, 2.1E-5F, 1.8E-5F, 1.7E-5F, 3.5E-5F, 2.1E-5F, 1.9E-5F, 2.5E-5F, 1.7E-5F, 3.7E-5F, 1.8E-5F, 1.8E-5F, 1.9E-5F, 2.2E-5F, 3.3E-5F, 3.2E-5F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
            mFirstByteStdDev = 0.020606F;
            mFirstByteMean = 0.010638F;
            mFirstByteWeight = 0.675261F;
            mSecondByteFreq = new float[]{0.020256F, 0.003293F, 0.045811F, 0.01665F, 0.007066F, 0.004146F, 0.009229F, 0.007333F, 0.003296F, 0.005239F, 0.008282F, 0.003791F, 0.006116F, 0.003536F, 0.004024F, 0.016654F, 0.009334F, 0.005429F, 0.033392F, 0.006121F, 0.008983F, 0.002801F, 0.004221F, 0.010357F, 0.014695F, 0.077937F, 0.006314F, 0.00402F, 0.007331F, 0.00715F, 0.005341F, 0.009195F, 0.00535F, 0.005698F, 0.004472F, 0.007242F, 0.004039F, 0.011154F, 0.016184F, 0.004741F, 0.012814F, 0.007679F, 0.008045F, 0.016631F, 0.009451F, 0.016487F, 0.007287F, 0.012688F, 0.017421F, 0.013205F, 0.03148F, 0.003404F, 0.009149F, 0.008921F, 0.007514F, 0.008683F, 0.008203F, 0.031403F, 0.011733F, 0.015617F, 0.015306F, 0.004004F, 0.010899F, 0.009961F, 0.008388F, 0.01092F, 0.003925F, 0.008585F, 0.009108F, 0.015546F, 0.004659F, 0.006934F, 0.007023F, 0.020252F, 0.005387F, 0.024704F, 0.006963F, 0.002625F, 0.009512F, 0.002971F, 0.008233F, 0.01F, 0.011973F, 0.010553F, 0.005945F, 0.006349F, 0.009401F, 0.008577F, 0.008186F, 0.008159F, 0.005033F, 0.008714F, 0.010614F, 0.006554F};
            mSecondByteStdDev = 0.009909F;
            mSecondByteMean = 0.010638F;
            mSecondByteWeight = 0.324739F;
        }
    }

    /**
     *
     */
    public static class nsBIG5Verifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsBIG5Verifier() {
            cclass = new int[32];
            cclass[0] = 286331153;
            cclass[1] = 1118481;
            cclass[2] = 286331153;
            cclass[3] = 286327057;
            cclass[4] = 286331153;
            cclass[5] = 286331153;
            cclass[6] = 286331153;
            cclass[7] = 286331153;
            cclass[8] = 572662306;
            cclass[9] = 572662306;
            cclass[10] = 572662306;
            cclass[11] = 572662306;
            cclass[12] = 572662306;
            cclass[13] = 572662306;
            cclass[14] = 572662306;
            cclass[15] = 304226850;
            cclass[16] = 1145324612;
            cclass[17] = 1145324612;
            cclass[18] = 1145324612;
            cclass[19] = 1145324612;
            cclass[20] = 858993460;
            cclass[21] = 858993459;
            cclass[22] = 858993459;
            cclass[23] = 858993459;
            cclass[24] = 858993459;
            cclass[25] = 858993459;
            cclass[26] = 858993459;
            cclass[27] = 858993459;
            cclass[28] = 858993459;
            cclass[29] = 858993459;
            cclass[30] = 858993459;
            cclass[31] = 53687091;
            states = new int[3];
            states[0] = 286339073;
            states[1] = 304226833;
            states[2] = 1;
            charset = "Big5";
            stFactor = 5;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class GB2312Statistics extends nsEUCStatistics {
        static float[] mFirstByteFreq;
        static float mFirstByteStdDev;
        static float mFirstByteMean;
        static float mFirstByteWeight;
        static float[] mSecondByteFreq;
        static float mSecondByteStdDev;
        static float mSecondByteMean;
        static float mSecondByteWeight;

        public float[] mFirstByteFreq() {
            return mFirstByteFreq;
        }

        public float mFirstByteStdDev() {
            return mFirstByteStdDev;
        }

        public float mFirstByteMean() {
            return mFirstByteMean;
        }

        public float mFirstByteWeight() {
            return mFirstByteWeight;
        }

        public float[] mSecondByteFreq() {
            return mSecondByteFreq;
        }

        public float mSecondByteStdDev() {
            return mSecondByteStdDev;
        }

        public float mSecondByteMean() {
            return mSecondByteMean;
        }

        public float mSecondByteWeight() {
            return mSecondByteWeight;
        }

        public GB2312Statistics() {
            mFirstByteFreq = new float[]{0.011628F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.011628F, 0.012403F, 0.009302F, 0.003876F, 0.017829F, 0.037209F, 0.008527F, 0.010078F, 0.01938F, 0.054264F, 0.010078F, 0.041085F, 0.02093F, 0.018605F, 0.010078F, 0.013178F, 0.016279F, 0.006202F, 0.009302F, 0.017054F, 0.011628F, 0.008527F, 0.004651F, 0.006202F, 0.017829F, 0.024806F, 0.020155F, 0.013953F, 0.032558F, 0.035659F, 0.068217F, 0.010853F, 0.036434F, 0.117054F, 0.027907F, 0.100775F, 0.010078F, 0.017829F, 0.062016F, 0.012403F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.00155F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F};
            mFirstByteStdDev = 0.020081F;
            mFirstByteMean = 0.010638F;
            mFirstByteWeight = 0.586533F;
            mSecondByteFreq = new float[]{0.006202F, 0.031008F, 0.005426F, 0.003101F, 0.00155F, 0.003101F, 0.082171F, 0.014729F, 0.006977F, 0.00155F, 0.013953F, 0.0F, 0.013953F, 0.010078F, 0.008527F, 0.006977F, 0.004651F, 0.003101F, 0.003101F, 0.003101F, 0.008527F, 0.003101F, 0.005426F, 0.005426F, 0.005426F, 0.003101F, 0.00155F, 0.006202F, 0.014729F, 0.010853F, 0.0F, 0.011628F, 0.0F, 0.031783F, 0.013953F, 0.030233F, 0.039535F, 0.008527F, 0.015504F, 0.0F, 0.003101F, 0.008527F, 0.016279F, 0.005426F, 0.00155F, 0.013953F, 0.013953F, 0.044961F, 0.003101F, 0.004651F, 0.006977F, 0.00155F, 0.005426F, 0.012403F, 0.00155F, 0.015504F, 0.0F, 0.006202F, 0.00155F, 0.0F, 0.007752F, 0.006977F, 0.00155F, 0.009302F, 0.011628F, 0.004651F, 0.010853F, 0.012403F, 0.017829F, 0.005426F, 0.024806F, 0.0F, 0.006202F, 0.0F, 0.082171F, 0.015504F, 0.004651F, 0.0F, 0.006977F, 0.004651F, 0.0F, 0.008527F, 0.012403F, 0.004651F, 0.003876F, 0.003101F, 0.022481F, 0.024031F, 0.00155F, 0.047287F, 0.009302F, 0.00155F, 0.005426F, 0.017054F};
            mSecondByteStdDev = 0.014156F;
            mSecondByteMean = 0.010638F;
            mSecondByteWeight = 0.413467F;
        }
    }

    /**
     *
     */
    public static class nsGB2312Verifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsGB2312Verifier() {
            cclass = new int[32];
            cclass[0] = 286331153;
            cclass[1] = 1118481;
            cclass[2] = 286331153;
            cclass[3] = 286327057;
            cclass[4] = 286331153;
            cclass[5] = 286331153;
            cclass[6] = 286331153;
            cclass[7] = 286331153;
            cclass[8] = 286331153;
            cclass[9] = 286331153;
            cclass[10] = 286331153;
            cclass[11] = 286331153;
            cclass[12] = 286331153;
            cclass[13] = 286331153;
            cclass[14] = 286331153;
            cclass[15] = 286331153;
            cclass[16] = 0;
            cclass[17] = 0;
            cclass[18] = 0;
            cclass[19] = 0;
            cclass[20] = 572662304;
            cclass[21] = 858993442;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662306;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 572662306;
            cclass[29] = 572662306;
            cclass[30] = 572662306;
            cclass[31] = 35791394;
            states = new int[2];
            states[0] = 286331649;
            states[1] = 1122850;
            charset = "GB2312";
            stFactor = 4;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsGB18030Verifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsGB18030Verifier() {
            cclass = new int[32];
            cclass[0] = 286331153;
            cclass[1] = 1118481;
            cclass[2] = 286331153;
            cclass[3] = 286327057;
            cclass[4] = 286331153;
            cclass[5] = 286331153;
            cclass[6] = 858993459;
            cclass[7] = 286331187;
            cclass[8] = 572662306;
            cclass[9] = 572662306;
            cclass[10] = 572662306;
            cclass[11] = 572662306;
            cclass[12] = 572662306;
            cclass[13] = 572662306;
            cclass[14] = 572662306;
            cclass[15] = 1109533218;
            cclass[16] = 1717986917;
            cclass[17] = 1717986918;
            cclass[18] = 1717986918;
            cclass[19] = 1717986918;
            cclass[20] = 1717986918;
            cclass[21] = 1717986918;
            cclass[22] = 1717986918;
            cclass[23] = 1717986918;
            cclass[24] = 1717986918;
            cclass[25] = 1717986918;
            cclass[26] = 1717986918;
            cclass[27] = 1717986918;
            cclass[28] = 1717986918;
            cclass[29] = 1717986918;
            cclass[30] = 1717986918;
            cclass[31] = 107374182;
            states = new int[6];
            states[0] = 318767105;
            states[1] = 571543825;
            states[2] = 17965602;
            states[3] = 286326804;
            states[4] = 303109393;
            states[5] = 17;
            charset = "GB18030";
            stFactor = 7;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsISO2022CNVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsISO2022CNVerifier() {
            cclass = new int[32];
            cclass[0] = 2;
            cclass[1] = 0;
            cclass[2] = 0;
            cclass[3] = 4096;
            cclass[4] = 0;
            cclass[5] = 48;
            cclass[6] = 0;
            cclass[7] = 0;
            cclass[8] = 16384;
            cclass[9] = 0;
            cclass[10] = 0;
            cclass[11] = 0;
            cclass[12] = 0;
            cclass[13] = 0;
            cclass[14] = 0;
            cclass[15] = 0;
            cclass[16] = 572662306;
            cclass[17] = 572662306;
            cclass[18] = 572662306;
            cclass[19] = 572662306;
            cclass[20] = 572662306;
            cclass[21] = 572662306;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662306;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 572662306;
            cclass[29] = 572662306;
            cclass[30] = 572662306;
            cclass[31] = 572662306;
            states = new int[8];
            states[0] = 304;
            states[1] = 286331152;
            states[2] = 572662289;
            states[3] = 336663074;
            states[4] = 286335249;
            states[5] = 286331237;
            states[6] = 286335249;
            states[7] = 18944273;
            charset = "ISO-2022-CN";
            stFactor = 9;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsISO2022JPVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsISO2022JPVerifier() {
            cclass = new int[32];
            cclass[0] = 2;
            cclass[1] = 570425344;
            cclass[2] = 0;
            cclass[3] = 4096;
            cclass[4] = 458752;
            cclass[5] = 3;
            cclass[6] = 0;
            cclass[7] = 0;
            cclass[8] = 1030;
            cclass[9] = 1280;
            cclass[10] = 0;
            cclass[11] = 0;
            cclass[12] = 0;
            cclass[13] = 0;
            cclass[14] = 0;
            cclass[15] = 0;
            cclass[16] = 572662306;
            cclass[17] = 572662306;
            cclass[18] = 572662306;
            cclass[19] = 572662306;
            cclass[20] = 572662306;
            cclass[21] = 572662306;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662306;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 572662306;
            cclass[29] = 572662306;
            cclass[30] = 572662306;
            cclass[31] = 572662306;
            states = new int[6];
            states[0] = 304;
            states[1] = 286331153;
            states[2] = 572662306;
            states[3] = 1091653905;
            states[4] = 303173905;
            states[5] = 287445265;
            charset = "ISO-2022-JP";
            stFactor = 8;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsISO2022KRVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsISO2022KRVerifier() {
            cclass = new int[32];
            cclass[0] = 2;
            cclass[1] = 0;
            cclass[2] = 0;
            cclass[3] = 4096;
            cclass[4] = 196608;
            cclass[5] = 64;
            cclass[6] = 0;
            cclass[7] = 0;
            cclass[8] = 20480;
            cclass[9] = 0;
            cclass[10] = 0;
            cclass[11] = 0;
            cclass[12] = 0;
            cclass[13] = 0;
            cclass[14] = 0;
            cclass[15] = 0;
            cclass[16] = 572662306;
            cclass[17] = 572662306;
            cclass[18] = 572662306;
            cclass[19] = 572662306;
            cclass[20] = 572662306;
            cclass[21] = 572662306;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662306;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 572662306;
            cclass[29] = 572662306;
            cclass[30] = 572662306;
            cclass[31] = 572662306;
            states = new int[5];
            states[0] = 285212976;
            states[1] = 572657937;
            states[2] = 289476898;
            states[3] = 286593297;
            states[4] = 8465;
            charset = "ISO-2022-KR";
            stFactor = 6;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsUCS2BEVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsUCS2BEVerifier() {
            cclass = new int[32];
            cclass[0] = 0;
            cclass[1] = 2097408;
            cclass[2] = 0;
            cclass[3] = 12288;
            cclass[4] = 0;
            cclass[5] = 3355440;
            cclass[6] = 0;
            cclass[7] = 0;
            cclass[8] = 0;
            cclass[9] = 0;
            cclass[10] = 0;
            cclass[11] = 0;
            cclass[12] = 0;
            cclass[13] = 0;
            cclass[14] = 0;
            cclass[15] = 0;
            cclass[16] = 0;
            cclass[17] = 0;
            cclass[18] = 0;
            cclass[19] = 0;
            cclass[20] = 0;
            cclass[21] = 0;
            cclass[22] = 0;
            cclass[23] = 0;
            cclass[24] = 0;
            cclass[25] = 0;
            cclass[26] = 0;
            cclass[27] = 0;
            cclass[28] = 0;
            cclass[29] = 0;
            cclass[30] = 0;
            cclass[31] = 1409286144;
            states = new int[7];
            states[0] = 288626549;
            states[1] = 572657937;
            states[2] = 291923490;
            states[3] = 1713792614;
            states[4] = 393569894;
            states[5] = 1717659269;
            states[6] = 1140326;
            charset = "UTF-16BE";
            stFactor = 6;
        }

        public boolean isUCS2() {
            return true;
        }
    }

    /**
     *
     */
    public static class nsUCS2LEVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsUCS2LEVerifier() {
            cclass = new int[32];
            cclass[0] = 0;
            cclass[1] = 2097408;
            cclass[2] = 0;
            cclass[3] = 12288;
            cclass[4] = 0;
            cclass[5] = 3355440;
            cclass[6] = 0;
            cclass[7] = 0;
            cclass[8] = 0;
            cclass[9] = 0;
            cclass[10] = 0;
            cclass[11] = 0;
            cclass[12] = 0;
            cclass[13] = 0;
            cclass[14] = 0;
            cclass[15] = 0;
            cclass[16] = 0;
            cclass[17] = 0;
            cclass[18] = 0;
            cclass[19] = 0;
            cclass[20] = 0;
            cclass[21] = 0;
            cclass[22] = 0;
            cclass[23] = 0;
            cclass[24] = 0;
            cclass[25] = 0;
            cclass[26] = 0;
            cclass[27] = 0;
            cclass[28] = 0;
            cclass[29] = 0;
            cclass[30] = 0;
            cclass[31] = 1409286144;
            states = new int[7];
            states[0] = 288647014;
            states[1] = 572657937;
            states[2] = 303387938;
            states[3] = 1712657749;
            states[4] = 357927015;
            states[5] = 1427182933;
            states[6] = 1381717;
            charset = "UTF-16LE";
            stFactor = 6;
        }

        public boolean isUCS2() {
            return true;
        }
    }

    /**
     *
     */
    public static class nsCP1252Verifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsCP1252Verifier() {
            cclass = new int[32];
            cclass[0] = 572662305;
            cclass[1] = 2236962;
            cclass[2] = 572662306;
            cclass[3] = 572654114;
            cclass[4] = 572662306;
            cclass[5] = 572662306;
            cclass[6] = 572662306;
            cclass[7] = 572662306;
            cclass[8] = 572662306;
            cclass[9] = 572662306;
            cclass[10] = 572662306;
            cclass[11] = 572662306;
            cclass[12] = 572662306;
            cclass[13] = 572662306;
            cclass[14] = 572662306;
            cclass[15] = 572662306;
            cclass[16] = 572662274;
            cclass[17] = 16851234;
            cclass[18] = 572662304;
            cclass[19] = 285286690;
            cclass[20] = 572662306;
            cclass[21] = 572662306;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 286331153;
            cclass[25] = 286331153;
            cclass[26] = 554766609;
            cclass[27] = 286331153;
            cclass[28] = 286331153;
            cclass[29] = 286331153;
            cclass[30] = 554766609;
            cclass[31] = 286331153;
            states = new int[3];
            states[0] = 571543601;
            states[1] = 340853778;
            states[2] = 65;
            charset = "windows-1252";
            stFactor = 3;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsHZVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsHZVerifier() {
            cclass = new int[32];
            cclass[0] = 1;
            cclass[1] = 0;
            cclass[2] = 0;
            cclass[3] = 4096;
            cclass[4] = 0;
            cclass[5] = 0;
            cclass[6] = 0;
            cclass[7] = 0;
            cclass[8] = 0;
            cclass[9] = 0;
            cclass[10] = 0;
            cclass[11] = 0;
            cclass[12] = 0;
            cclass[13] = 0;
            cclass[14] = 0;
            cclass[15] = 38813696;
            cclass[16] = 286331153;
            cclass[17] = 286331153;
            cclass[18] = 286331153;
            cclass[19] = 286331153;
            cclass[20] = 286331153;
            cclass[21] = 286331153;
            cclass[22] = 286331153;
            cclass[23] = 286331153;
            cclass[24] = 286331153;
            cclass[25] = 286331153;
            cclass[26] = 286331153;
            cclass[27] = 286331153;
            cclass[28] = 286331153;
            cclass[29] = 286331153;
            cclass[30] = 286331153;
            cclass[31] = 286331153;
            states = new int[6];
            states[0] = 285213456;
            states[1] = 572657937;
            states[2] = 335548706;
            states[3] = 341120533;
            states[4] = 336872468;
            states[5] = 36;
            charset = "HZ-GB-2312";
            stFactor = 6;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsSJISVerifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsSJISVerifier() {
            cclass = new int[32];
            cclass[0] = 286331152;
            cclass[1] = 1118481;
            cclass[2] = 286331153;
            cclass[3] = 286327057;
            cclass[4] = 286331153;
            cclass[5] = 286331153;
            cclass[6] = 286331153;
            cclass[7] = 286331153;
            cclass[8] = 572662306;
            cclass[9] = 572662306;
            cclass[10] = 572662306;
            cclass[11] = 572662306;
            cclass[12] = 572662306;
            cclass[13] = 572662306;
            cclass[14] = 572662306;
            cclass[15] = 304226850;
            cclass[16] = 858993459;
            cclass[17] = 858993459;
            cclass[18] = 858993459;
            cclass[19] = 858993459;
            cclass[20] = 572662308;
            cclass[21] = 572662306;
            cclass[22] = 572662306;
            cclass[23] = 572662306;
            cclass[24] = 572662306;
            cclass[25] = 572662306;
            cclass[26] = 572662306;
            cclass[27] = 572662306;
            cclass[28] = 858993459;
            cclass[29] = 1145393971;
            cclass[30] = 1145324612;
            cclass[31] = 279620;
            states = new int[3];
            states[0] = 286339073;
            states[1] = 572657937;
            states[2] = 4386;
            charset = "Shift_JIS";
            stFactor = 6;
        }

        public boolean isUCS2() {
            return false;
        }
    }

    /**
     *
     */
    public static class nsUTF8Verifier extends nsVerifier {
        static int[] cclass;
        static int[] states;
        static int stFactor;
        static String charset;

        public int[] cclass() {
            return cclass;
        }

        public int[] states() {
            return states;
        }

        public int stFactor() {
            return stFactor;
        }

        public String charset() {
            return charset;
        }

        public nsUTF8Verifier() {
            cclass = new int[32];
            cclass[0] = 286331153;
            cclass[1] = 1118481;
            cclass[2] = 286331153;
            cclass[3] = 286327057;
            cclass[4] = 286331153;
            cclass[5] = 286331153;
            cclass[6] = 286331153;
            cclass[7] = 286331153;
            cclass[8] = 286331153;
            cclass[9] = 286331153;
            cclass[10] = 286331153;
            cclass[11] = 286331153;
            cclass[12] = 286331153;
            cclass[13] = 286331153;
            cclass[14] = 286331153;
            cclass[15] = 286331153;
            cclass[16] = 858989090;
            cclass[17] = 1145324612;
            cclass[18] = 1145324612;
            cclass[19] = 1145324612;
            cclass[20] = 1431655765;
            cclass[21] = 1431655765;
            cclass[22] = 1431655765;
            cclass[23] = 1431655765;
            cclass[24] = 1717986816;
            cclass[25] = 1717986918;
            cclass[26] = 1717986918;
            cclass[27] = 1717986918;
            cclass[28] = -2004318073;
            cclass[29] = -2003269496;
            cclass[30] = -1145324614;
            cclass[31] = 16702940;
            states = new int[26];
            states[0] = -1408167679;
            states[1] = 878082233;
            states[2] = 286331153;
            states[3] = 286331153;
            states[4] = 572662306;
            states[5] = 572662306;
            states[6] = 290805009;
            states[7] = 286331153;
            states[8] = 290803985;
            states[9] = 286331153;
            states[10] = 293041937;
            states[11] = 286331153;
            states[12] = 293015825;
            states[13] = 286331153;
            states[14] = 295278865;
            states[15] = 286331153;
            states[16] = 294719761;
            states[17] = 286331153;
            states[18] = 298634257;
            states[19] = 286331153;
            states[20] = 297865489;
            states[21] = 286331153;
            states[22] = 287099921;
            states[23] = 286331153;
            states[24] = 285212689;
            states[25] = 286331153;
            charset = "UTF-8";
            stFactor = 16;
        }

        public boolean isUCS2() {
            return false;
        }
    }

}
