package cn.com.lxsoft.bdasphone.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.com.lxsoft.bdasphone.utils.ConvertUtils;

public class BridgeEvaluation {
    YearTest yearTest;

    public BridgeEvaluation(YearTest yt){
        yearTest=yt;
    }

    public class PJData{
        public int pjFen;
        public int upFen;
        public int dwFen;
        public int sfFen;
        public String bjFen;
    }

    class GjSt{
        public float score;
        public List<String> bhList;
    }

    class BjSt{
        public float score;
        public float weight;
        public HashMap<String,GjSt> gjMap;
    }

    public void evaluateTestData(String[] typeList,List<Disease> dList, List<Construct> consList,ArrayList<PJData> pjDataList) {

        for(int n=0;n<typeList.length;n++) {
            float upScore=0,dwScore=0,sfScore=0;
            String resBjScore="";
            HashMap<String, Float> wtMap = DataDict.getConstructWeight(typeList[n]);

            HashMap<String, BjSt> bjMap = new HashMap<>();
            for (int i = 0; i < consList.size(); i++) {
                Construct ct = consList.get(i);
                if(!ct.getXuHao().equals(typeList[n]))
                    continue;
                BjSt bjData;
                if (bjMap.containsKey(ct.getBuJian())) {
                    bjData = bjMap.get(ct.getBuJian());
                } else {
                    bjData = new BjSt();
                    bjData.gjMap = new HashMap<>();
                    bjMap.put(ct.getBuJian(), bjData);
                    //bjData.weight = wtMap.get(ct.getBuJian());
                }

                String[] tplist = ct.getGouJian().split(",");
                for (int x = 0; x < tplist.length; x++) {
                    GjSt gjData = new GjSt();
                    bjData.gjMap.put(tplist[x] + ct.getCaiZhi(), gjData);
                    List<String> bhList = null;
                    for (int y = 0; y < dList.size(); y++) {
                        Disease di = dList.get(y);
                        if (di.getXuHao()==ct.getXuHao() && di.getBuJian().equals(ct.getBuJian()) && di.getGouJian().equals(tplist[x])) {
                            if (bhList == null)
                                bhList = new ArrayList();
                            bhList.add(di.getBingHai());
                        }
                        gjData.bhList = bhList;
                    }
                }
            }

            for (String bjKey : bjMap.keySet()) {
                HashMap<String, GjSt> gjMap = bjMap.get(bjKey).gjMap;
                float sumGj = 0, minGj = 100;
                boolean bLess40 = false;
                for (String gjKey : gjMap.keySet()) {
                    List<String> bhList = gjMap.get(gjKey).bhList;
                    float score = 100;
                    if (bhList != null) {
                        if (bhList.size() == 1) {
                            score = 100f - getDMark(bhList.get(0));
                        } else if (bhList.size() > 1) {
                            float sumU = getDMark(bhList.get(0));
                            int dp = 0;
                            for (int z = 1; z < bhList.size(); z++) {
                                dp = getDMark(bhList.get(z));
                                if (dp == 100) {
                                    score = 0;
                                    break;
                                }
                                sumU += dp * (100f - sumU) / 100f / (float) Math.sqrt(z);
                            }
                            if (score != 0)
                                score = 100f - sumU;
                        }
                    }
                    gjMap.get(gjKey).score = score;
                    sumGj += score;
                    if (score < minGj)
                        minGj = score;
                    if (score < 40)
                        bLess40 = true;
                }
                float bjScore = 0;
                if (bLess40)
                    bjScore = minGj;
                else
                    bjScore = sumGj / gjMap.size() - (100 - minGj) / getTMark(gjMap.size());
                bjMap.get(bjKey).score = bjScore;
                resBjScore += "," + bjKey + ":" + Integer.toString((int) bjScore);
                switch (bjKey.charAt(0)) {
                    case '1':
                        upScore += bjScore * wtMap.get(bjKey);
                        break;
                    case '2':
                        dwScore += bjScore * wtMap.get(bjKey);
                        break;
                    case '3':
                        sfScore += bjScore * wtMap.get(bjKey);
                        break;
                }
            }

            PJData pjData = new PJData();

            if(!ConvertUtils.isSpace(resBjScore))
                pjData.bjFen = resBjScore.substring(1);
            pjData.upFen = (int) upScore;
            pjData.dwFen = (int) dwScore;
            pjData.sfFen = (int) sfScore;

            pjData.pjFen = (int) (upScore * wtMap.get("100") + dwScore * wtMap.get("200") + sfScore * wtMap.get("300"));
            pjDataList.add(pjData);
        }

        int smallFen=pjDataList.get(0).pjFen;
        PJData chData=pjDataList.get(0);
        for(int i=1;i<pjDataList.size();i++){
            if(pjDataList.get(i).pjFen<smallFen){
                chData=pjDataList.get(i);
                smallFen=chData.pjFen;
            }
        }

        yearTest.setGouJianPingFen(chData.bjFen);
        yearTest.setShangBuJieGouPingFen(chData.upFen);
        yearTest.setXiaBuJieGouPingFen(chData.dwFen);
        yearTest.setQiaoMianXiPingFen(chData.sfFen);
        yearTest.setPingFen(chData.pjFen);

        int pf=chData.pjFen;

        int pingJi=1;
        if(pf<40)
            pingJi=5;
        else if (pf<60)
            pingJi=4;
        else if(pf<78)
            pingJi=3;
        else if(pf<90)
            pingJi=2;

        for(int i=0;i<dList.size();i++){
            if(dList.get(i).getBuJian().equals("401")) {
                pingJi = 5;
                break;
            }
        }
        yearTest.setPingJia(pingJi);
    }

    static int[][] markMx={{0,20,35},{ 0,25,40,50},{0,35,45,60,100}};
    protected int getDMark(String dID){
        if(dID.length()<8)
            return 0;
        int highDJ=Integer.parseInt(dID.substring(7,8));
        int dDJ=Integer.parseInt(dID.substring(8,9));
        return markMx[highDJ-3][dDJ-1];
    }

    static float[] tMarkMx={Float.POSITIVE_INFINITY,10, 9.7f, 10.5f, 10.2f, 8.9f, 8.7f, 8.5f, 9.3f, 9.1f, 8.8f, 8.7f, 8.5f, 8.3f, 8.2f, 7.08f, 6.96f, 6.84f, 6.72f, 6.6f, 7.48f, 7.36f, 7.24f, 7.12f, 6.00f, 5.88f, 5.76f, 5.64f, 5.52f, 5.4f, 4.9f, 4.4f, 4.0f, 3.6f, 3.2f, 2.8f, 2.5f, 2.3f};

    protected float getTMark(int size){
        if(size>100)
            return 2.3f;
        else if(size>=30)
            return tMarkMx[26+(int)Math.floor(size/10)];
        return tMarkMx[size-1];
    }

}
