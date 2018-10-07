package com.hamdani.agecalculator;

import android.app.ActivityManager;
import android.app.DialogFragment;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements communicator {

    private InterstitialAd mInterstitialAd;
    int _day,_month,_year=2000,_currentDay,_currentYear,_currentMonth,_ageY,_ageM,_ageD,_nextMonths,_nextDays;
    String[] upcomingBirthday=new String[15],weekday=new String[8];
    LinearLayout _ll_UBC;
    boolean check=false,checkthirtyfirst=false;
    MediaPlayer _sound_Calculate,_sound_Clear;
    int count=0;

    TextView _tv_dateOfBirth,_tv_currentDate,_ageYears,_ageMonths,_ageDays,
    _tv_eTotalYears,_tv_eTotalMonths,_tv_eTotalWeeks,_tv_eTotalDays,_tv_eTotalHours,_tv_eTotalMinuts,_tv_eTotalSeconds,
    _tv_UBC,_tv_firtDOB,_tv_secondDOB,_tv_thirdDOB,_tv_fourthDOB,_tv_fifthDOB,_tv_sixthDOB,_tv_seventhDOB,
            _tv_dayfirtDOB,_tv_daysecondDOB,_tv_daythirdDOB,_tv_dayfourthDOB,_tv_dayfifthDOB,_tv_daysixthDOB,_tv_dayseventhDOB;
TextView _tv_nextBdMonth,_tv_nextBdDays,_tv_currentDayofWeek,_tv_birthDayofWeek;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showInterAdd();
        initializer();

        mAdView =(AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



    }

    private void initializer() {
        _sound_Calculate=MediaPlayer.create(this,R.raw.zapsplat_sport_soccer_ball_goal_score_ball_hit_goal_net_001_12518);
        _sound_Clear=MediaPlayer.create(this,R.raw.zapsplat_household_trash_rubbish_in_trash_can_compress_squash_down_001_14406);
        _tv_eTotalYears= (TextView) findViewById(R.id.tv_extraTotalyears);
        _tv_eTotalMonths= (TextView) findViewById(R.id.tv_extraTotalMonths);
        _tv_eTotalWeeks= (TextView) findViewById(R.id.tv_extraTotalWeeks);
        _tv_eTotalDays= (TextView) findViewById(R.id.tv_extraTotalDays);
        _tv_eTotalHours= (TextView) findViewById(R.id.tv_extraTotalHours);
        _tv_eTotalMinuts= (TextView) findViewById(R.id.tv_extraTotalMinuts);
        _tv_eTotalSeconds= (TextView) findViewById(R.id.tv_extraTotalSeconds);

        _tv_dateOfBirth= (TextView) findViewById(R.id.tv_dateOfBirth);
        _tv_currentDate= (TextView) findViewById(R.id.tv_currentDate);
        _ageYears= (TextView) findViewById(R.id.tv_ageYears);
        _ageMonths= (TextView) findViewById(R.id.tv_ageMonths);
        _ageDays= (TextView) findViewById(R.id.tv_ageDays);

        _tv_UBC= (TextView) findViewById(R.id.tv_UBC);
        _ll_UBC= (LinearLayout) findViewById(R.id.ll_UBC);
        _tv_firtDOB= (TextView) findViewById(R.id.tv_istUCB);
        _tv_secondDOB= (TextView) findViewById(R.id.tv_secondUCB);
        _tv_thirdDOB= (TextView) findViewById(R.id.tv_thirdUCB);
        _tv_fourthDOB= (TextView) findViewById(R.id.tv_fourthUCB);
        _tv_fifthDOB= (TextView) findViewById(R.id.tv_fifthUCB);
        _tv_sixthDOB= (TextView) findViewById(R.id.tv_sixthUCB);
        _tv_seventhDOB= (TextView) findViewById(R.id.tv_seventhUCB);

        _tv_dayfirtDOB= (TextView) findViewById(R.id.tv_dayZeroUCB);
        _tv_daysecondDOB= (TextView) findViewById(R.id.tv_dayfirstUCB);
        _tv_daythirdDOB= (TextView) findViewById(R.id.tv_daySecondUCB);
        _tv_dayfourthDOB= (TextView) findViewById(R.id.tv_dayThirdUCB);
        _tv_dayfifthDOB= (TextView) findViewById(R.id.tv_dayforthUCB);
        _tv_daysixthDOB= (TextView) findViewById(R.id.tv_dayfiveUCB);
        _tv_dayseventhDOB= (TextView) findViewById(R.id.tv_daysixthUCB);

        _tv_nextBdDays= (TextView) findViewById(R.id.tv_nextbdDays);

        _tv_nextBdMonth= (TextView) findViewById(R.id.tv_nextbdMonth);
        _tv_currentDayofWeek=(TextView)findViewById(R.id.tv_currentDayofWeek);
        _tv_birthDayofWeek=(TextView)findViewById(R.id.tv_BirthDayofWeek);

        Calendar calender=Calendar.getInstance();
        _currentDay=calender.get(Calendar.DAY_OF_MONTH);
        _currentYear=calender.get(Calendar.YEAR);
        _currentMonth=calender.get(Calendar.MONTH);
        Date day=calender.getTime();
        String Dayweek=findDay(day);
        _tv_currentDayofWeek.setText(Dayweek);

        _tv_currentDate.setText(String.valueOf(_currentDay)+"-"+String.valueOf(_currentMonth+1)+"-"+String.valueOf(_currentYear));
    }

    public void selectDateofBirth(View view) {
        DialogFragment newFragment = new DatePickerFragment(this);
        Bundle bundle=new Bundle();
        bundle.putString("bDay",String.valueOf(_day));
        bundle.putString("bMonth",String.valueOf(_month));
        bundle.putString("bYear",String.valueOf(_year));
        bundle.putString("which","dob");
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(),"dd");
    }
    public void currentDate(View view) {
        DialogFragment newFragment = new DatePickerFragment(this);
        Bundle bundle=new Bundle();
        bundle.putString("cDay",String.valueOf(_currentDay));
        bundle.putString("cMonth",String.valueOf(_currentMonth));
        bundle.putString("cYear",String.valueOf(_currentYear));
        bundle.putString("which","cd");
        newFragment.setArguments(bundle);
        newFragment.show(getFragmentManager(),"dd");
    }

    @Override
    public void respond(int day, int month, int year,String which) {
        Date currentDate = null,birthDate=null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yyyy");

        if(which=="dob") {
            _day = year;
            _month = month;
            _year = day;
            String bd=String.valueOf(_day)+"/"+String.valueOf(_month+1)+"/"+String.valueOf(_year);

            try {
                birthDate=sdf.parse(bd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String Dayweek=findDay(birthDate);
            _tv_birthDayofWeek.setText(Dayweek);
            _tv_dateOfBirth.setText(String.format("%02d-%02d-%04d",_day ,(_month+1),_year));
            check=true;
        }else if(which=="cd")
        {
            _currentDay=year;
            _currentMonth=month;
            _currentYear=day;
            String cd=String.valueOf(_currentDay)+"/"+String.valueOf(_currentMonth+1)+"/"+String.valueOf(_currentYear);
            try {
                currentDate=sdf.parse(cd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String Dayweek=findDay(currentDate);
            _tv_currentDayofWeek.setText(Dayweek);
            _tv_currentDate.setText(String.format("%02d-%02d-%04d",_currentDay ,(_currentMonth+1),_currentYear));

        }

    }

    public void calculateMyAge(View view) {
//        _sound_Calculate.start();
        if(check) {
            Date currentDate = null, birthDate = null;
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String cd = String.valueOf(_currentDay) + "/" + String.valueOf(_currentMonth + 1) + "/" + String.valueOf(_currentYear);
            String bd = String.valueOf(_day) + "/" + String.valueOf(_month + 1) + "/" + String.valueOf(_year);

            try {
                currentDate = sdf.parse(cd);
                birthDate = sdf.parse(bd);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (birthDate.after(currentDate)) {
                Toast.makeText(this, "Birth date is greater than current date ", Toast.LENGTH_SHORT).show();

            } else {

                long different = currentDate.getTime() - birthDate.getTime();
                long secondsInMilli = 1000;
                long minutesInMilli = secondsInMilli * 60;
                long hoursInMilli = minutesInMilli * 60;
                long daysInMilli = hoursInMilli * 24;

                long elapsedDays = different / daysInMilli;

                long elapsedHours = different / hoursInMilli;

                long elapsedMinutes = different / minutesInMilli;

                long elapsedSeconds = different / secondsInMilli;
                long totalweeks = elapsedDays / 7;
                long totalmonths = elapsedDays / 30;
                long totalyear = totalmonths / 12;
                //For Totoal age
                agecalculate(birthDate, currentDate);

                _ageYears.setText(String.format("%02d",_ageY));
                _ageMonths.setText(String.format("%02d",_ageM));
                _ageDays.setText(String.format("%02d",_ageD));

                _tv_eTotalYears.setText(String.format("%02d", totalyear));
                _tv_eTotalMonths.setText(String.format("%02d", totalmonths));
                _tv_eTotalWeeks.setText(String.format("%02d", totalweeks));
                _tv_eTotalDays.setText(String.format("%02d", elapsedDays));
                _tv_eTotalHours.setText(String.format("%02d", elapsedHours));
                _tv_eTotalMinuts.setText(String.format("%02d", elapsedMinutes));
                _tv_eTotalSeconds.setText(String.format("%02d", elapsedSeconds));

                //Upcoming birthday year and day
                upcomingBirthday(birthDate);
                _ll_UBC.setVisibility(View.VISIBLE);
                _tv_UBC.setVisibility(View.VISIBLE);

                _tv_firtDOB.setText(upcomingBirthday[1]);
                _tv_secondDOB.setText(upcomingBirthday[2]);
                _tv_thirdDOB.setText(upcomingBirthday[3]);
                _tv_fourthDOB.setText(upcomingBirthday[4]);
                _tv_fifthDOB.setText(upcomingBirthday[5]);
                _tv_sixthDOB.setText(upcomingBirthday[6]);
                _tv_seventhDOB.setText(upcomingBirthday[7]);

                _tv_dayfirtDOB.setText(weekday[1]);
                _tv_daysecondDOB.setText(weekday[2]);
                _tv_daythirdDOB.setText(weekday[3]);
                _tv_dayfourthDOB.setText(weekday[4]);
                _tv_dayfifthDOB.setText(weekday[5]);
                _tv_daysixthDOB.setText(weekday[6]);
                _tv_dayseventhDOB.setText(weekday[7]);

                //NextBD
                next(birthDate, currentDate);

            }
        }else{
            Toast.makeText(this, "Select date of birth", Toast.LENGTH_SHORT).show();
        }



    }
    public void next(Date d,Date c)
    {
        //create calendar object for birth day
        Calendar b = Calendar.getInstance();
        b.setTimeInMillis(d.getTime());
        int birthDate = b.get(Calendar.DATE);
        if(birthDate==31)
        {
            d.setDate(30);
            b.setTimeInMillis(d.getTime());
            int bir= b.get(Calendar.DATE);
            checkthirtyfirst=true;
           // Toast.makeText(this, "D="+String.valueOf(bir), Toast.LENGTH_SHORT).show();
        }


        DateTime dob = new DateTime(d);
        LocalDate currentDate = new LocalDate(c);



        // Take birthDay  and birthMonth  from dateOfBirth
        int birthDay = dob.getDayOfMonth();
        int birthMonth = dob.getMonthOfYear();
        // Current year's birthday
        LocalDate currentYearBirthDay = null;
        try {
            currentYearBirthDay = new LocalDate().withDayOfMonth(birthDay)
                    .withMonthOfYear(birthMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        PeriodType monthDay = PeriodType.yearMonthDayTime().withYearsRemoved();
        PeriodFormatter periodFormatter = new PeriodFormatterBuilder()
                .appendMonths().appendSuffix(" Months ").appendDays()
                .appendSuffix(" Days ").printZeroNever().toFormatter();
        try {
            if (currentYearBirthDay.isAfter(currentDate)) {
                Period period = new Period(currentDate, currentYearBirthDay,monthDay );
                String currentBirthday = periodFormatter.print(period);
                _nextMonths=period.getMonths();
                _nextDays=period.getDays();
                if(checkthirtyfirst)
                {
                    _nextDays=_nextDays+1;
                    checkthirtyfirst=false;
                }
                System.out.println(currentBirthday );
            } else {
                LocalDate nextYearBirthDay =currentYearBirthDay.plusYears(1);
                Period period = new Period(currentDate, nextYearBirthDay ,monthDay );
                String nextBirthday = periodFormatter.print(period);
                _nextMonths=period.getMonths();
                _nextDays=period.getDays();
                if(checkthirtyfirst)
                {
                    _nextDays=_nextDays+1;
                    checkthirtyfirst=false;
                }
                System.out.println(nextBirthday);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        _tv_nextBdDays.setText(String.format("%02d",_nextDays));
        _tv_nextBdMonth.setText(String.format("%02d",_nextMonths));

    }
    public void upcomingBirthday(Date dob)
    {
        Date ubd = null;
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat show=new SimpleDateFormat("dd MMM yyyy");

        Calendar c=Calendar.getInstance();
        int currentYear=c.get(Calendar.YEAR);
        int birthDay=_day;
        int birthMonth=_month;


        String upcomingbd=String.valueOf(birthDay)+"-"+String.valueOf(birthMonth+1)+"-"+String.valueOf(currentYear);
        try {
            ubd=sdf.parse(upcomingbd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(int i=1;i<=7;i++) {
            String t = show.format(ubd);
            upcomingBirthday[i]=t;
            c.setTime(ubd);
            c.add(Calendar.YEAR,1);
            ubd=c.getTime();
            weekday[i]=findDay(ubd);
           // Toast.makeText(this, weekday[i], Toast.LENGTH_SHORT).show();
        }



    }
    public String findDay(Date dob)
    {
        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(dob.getTime());
        int day = c.get(Calendar.DAY_OF_WEEK)-1;
        String weekDay="";

        switch (day) {
            case Calendar.SUNDAY: weekDay = "Sunday"; break;
            case Calendar.MONDAY: weekDay = "Monday"; break;
            case Calendar.TUESDAY: weekDay = "Tuesday"; break;
            case Calendar.WEDNESDAY: weekDay = "Wednesday"; break;
            case Calendar.THURSDAY: weekDay = "Thursday"; break;
            case Calendar.FRIDAY: weekDay = "Friday"; break;
            case Calendar.SATURDAY: weekDay = "Saturday"; break;
        }

       return weekDay;
    }


    public void agecalculate(Date dob,Date cd)
    {
        int years = 0;
        int months = 0;
        int days = 0;
        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(dob.getTime());

        //create calendar object for current day
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(cd.getTime());

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        //Get difference between months
        months = currMonth - birthMonth;
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0)
        {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
        {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else
        {
            days = 0;
            if (months == 12)
            {
                years++;
                months = 0;
            }
        }
        _ageY=years;
        _ageM=months;
        _ageD=days;
    }


    public void clearData(View view) {
        _sound_Clear.start();
        if (check) {
            _tv_eTotalYears.setText("0");
            _tv_eTotalMonths.setText("0");
            _tv_eTotalWeeks.setText("0");
            _tv_eTotalDays.setText("0");
            _tv_eTotalHours.setText("0");
            _tv_eTotalMinuts.setText("0");
            _tv_eTotalSeconds.setText("0");
            _ll_UBC.setVisibility(View.GONE);
            _tv_UBC.setVisibility(View.GONE);

            _ageYears.setText(String.format("%02d", 0));
            _ageMonths.setText(String.format("%02d", 0));
            _ageDays.setText(String.format("%02d", 0));
            _tv_nextBdDays.setText(String.format("%02d", 0));
            _tv_nextBdMonth.setText(String.format("%02d", 0));
            //Current date
            Calendar calender = Calendar.getInstance();
            _currentDay = calender.get(Calendar.DAY_OF_MONTH);
            _currentYear = calender.get(Calendar.YEAR);
            _currentMonth = calender.get(Calendar.MONTH);
            Date day = calender.getTime();
            String Dayweek = findDay(day);
            _tv_currentDayofWeek.setText(Dayweek);
            _day = _currentDay;
            _month = _currentMonth;
            _year = _currentYear;
            _tv_dateOfBirth.setText(String.format("%02d-%02d-%04d", _day, (_month + 1), _year));
            _tv_birthDayofWeek.setText(Dayweek);

            _tv_currentDate.setText(String.valueOf(_currentDay) + "-" + String.valueOf(_currentMonth + 1) + "-" + String.valueOf(_currentYear));

        } else {
            Toast.makeText(this, "Enter date of birth", Toast.LENGTH_SHORT).show();
        }

    }
    public void showInterAdd() {
        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
         mInterstitialAd.setAdUnitId("ca-app-pub-3614349063418432/1333091141");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("deviceid").build());
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();

            }
        });


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
            super.onBackPressed();
           showInterAdd();
        }

}
