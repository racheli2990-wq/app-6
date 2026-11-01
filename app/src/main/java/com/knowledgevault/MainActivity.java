package com.knowledgevault;

import android.app.*;
import android.os.Bundle;
import android.content.*;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.text.*;
import java.util.*;

public class MainActivity extends Activity {
    int navy=Color.rgb(15,25,48), gold=Color.rgb(224,178,82), ink=Color.rgb(35,43,59), muted=Color.rgb(112,122,140), paper=Color.rgb(247,248,251);
    LinearLayout root, content, top;
    EditText search;
    String category="הכול", query="";
    boolean dark=false;
    Set<String> favorites=new HashSet<>(), history=new LinkedHashSet<>();
    android.content.SharedPreferences prefs;
    static class Article {
        String title, category, summary, body;
        Article(String t,String c,String s,String b){title=t;category=c;summary=s;body=b;}
    }
    ArrayList<Article> articles=new ArrayList<>();
    @Override public void onCreate(Bundle b){super.onCreate(b);
        prefs=getSharedPreferences("vault",0);
        favorites.addAll(prefs.getStringSet("favorites",new HashSet<>()));
        history.addAll(prefs.getStringSet("history",new LinkedHashSet<>()));
        seed(); home();
    }
    void seed(){
        add("מערכת השמש","מדע","מערכת הכוכבים שבה נמצא כדור הארץ.","מערכת השמש כוללת את השמש ואת כל הגופים הנעים בהשפעת כוח הכבידה שלה: שמונת כוכבי הלכת, ירחים, כוכבי לכת ננסיים, אסטרואידים ושביטים. השמש מכילה את רוב המסה של המערכת ומספקת לה אור ואנרגיה.");
        add("חורים שחורים","מדע","אזורים בחלל שבהם הכבידה חזקה במיוחד.","חור שחור הוא אזור במרחב־זמן שבו הכבידה חזקה עד כדי כך שאפילו אור אינו יכול להימלט ממנו לאחר חציית אופק האירועים. מדענים חוקרים אותם באמצעות השפעתם על חומר ואור בסביבתם.");
        add("האוקיינוס השקט","גאוגרפיה","האוקיינוס הגדול ביותר בכדור הארץ.","האוקיינוס השקט משתרע בין אסיה ואוסטרליה במערב לבין יבשות אמריקה במזרח. הוא מכסה יותר שטח מכל יבשות העולם יחד, ובו אלפי איים ותעלות עמוקות.");
        add("הר האוורסט","גאוגרפיה","ההר הגבוה ביותר מעל פני הים.","הר האוורסט נמצא ברכס ההימלאיה, על גבול נפאל וסין. גובהו המקובל הוא 8,848.86 מטרים. תנאי מזג האוויר, הקור והמחסור בחמצן הופכים את הטיפוס לפעילות מאתגרת במיוחד.");
        add("המחשב האישי","טכנולוגיה","מחשב המיועד לשימוש אישי.","מחשב אישי הוא מערכת מחשוב לשימוש של אדם יחיד בכל רגע נתון. הוא מורכב בדרך כלל ממעבד, זיכרון, אחסון, לוח אם והתקני קלט ופלט. מחשבים אישיים משמשים לעבודה, לימוד, יצירה ובידור.");
        add("בינה מלאכותית","טכנולוגיה","תחום המפתח מערכות המבצעות משימות הדורשות יכולות חכמות.","בינה מלאכותית היא תחום במדעי המחשב העוסק ביצירת מערכות המסוגלות לבצע משימות כגון זיהוי דפוסים, הבנת שפה, חיזוי וקבלת החלטות. מערכות מודרניות רבות לומדות מתוך כמויות גדולות של נתונים.");
        add("הדפוס","היסטוריה","טכנולוגיה ששינתה את הפצת הידע.","טכנולוגיית הדפוס באותיות נעות התפתחה באירופה במאה ה־15 וזוהתה במיוחד עם יוהנס גוטנברג. היא אפשרה לייצר ספרים בעותקים רבים, להוזיל את הפצתם ולהרחיב את הגישה למידע.");
        add("הפירמידות במצרים","היסטוריה","מבני אבן מונומנטליים מתקופת מצרים העתיקה.","הפירמידות בגיזה נבנו בתקופת הממלכה הקדומה במצרים ושימשו מתחמי קבורה מלכותיים. הפירמידה הגדולה נבנתה עבור פרעה ח'ופו והיא אחת היצירות ההנדסיות המרשימות של העולם העתיק.");
        add("תאים חיים","ביולוגיה","יחידות המבנה והתפקוד הבסיסיות של יצורים חיים.","התא הוא יחידת החיים הבסיסית. תאים מכילים חומר תורשתי ומבנים המאפשרים להם לקלוט אנרגיה, לבנות מולקולות, להגיב לסביבה ולהתרבות. יש תאים פשוטים ללא גרעין ותאים מורכבים בעלי גרעין.");
        add("פוטוסינתזה","ביולוגיה","תהליך שבו צמחים ממירים אנרגיית אור לאנרגיה כימית.","בפוטוסינתזה צמחים, אצות וחיידקים מסוימים משתמשים באנרגיית אור כדי לייצר תרכובות אורגניות מפחמן דו־חמצני ומים. בתהליך נפלט בדרך כלל חמצן, והוא בסיס מרכזי לרבות משרשראות המזון.");
        add("הירח","חלל","הלוויין הטבעי של כדור הארץ.","הירח הוא הגוף השמימי הקרוב ביותר לכדור הארץ. כוח הכבידה שלו משפיע על הגאות והשפל, ומחזור מופעיו נובע מהגאומטריה המשתנה בין השמש, הארץ והירח.");
        add("מים","מדע","תרכובת כימית חיונית לחיים המוכרים לנו.","מים הם תרכובת של מימן וחמצן, שנוסחתה H₂O. הם מופיעים בטבע בשלושת מצבי הצבירה, ממיסים חומרים רבים ומשתתפים בתהליכים ביולוגיים, גאולוגיים ואקלימיים.");
    }
    void add(String t,String c,String s,String b){articles.add(new Article(t,c,s,b));}
    void save(){prefs.edit().putStringSet("favorites",new HashSet<>(favorites)).putStringSet("history",new LinkedHashSet<>(history)).apply();}
    TextView text(String s,int size,int color,boolean bold){TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(color);if(bold)v.setTypeface(Typeface.DEFAULT,Typeface.BOLD);v.setGravity(Gravity.RIGHT);return v;}
    GradientDrawable bg(int color,int radius){GradientDrawable d=new GradientDrawable();d.setColor(color);d.setCornerRadius(radius);return d;}
    void base(String title){
        dark=prefs.getBoolean("dark",false); int background=dark?Color.rgb(18,25,39):paper, fg=dark?Color.WHITE:ink;
        root=new LinearLayout(this);root.setOrientation(1);root.setBackgroundColor(background);setContentView(root);
        top=new LinearLayout(this);top.setPadding(20,18,20,16);top.setGravity(Gravity.CENTER_VERTICAL);top.setBackgroundColor(navy);root.addView(top);
        TextView brand=text("KNOWLEDGE VAULT",12,gold,true);brand.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);top.addView(brand,new LinearLayout.LayoutParams(0,-2,1));
        TextView label=text(title,19,Color.WHITE,true);top.addView(label);
        ScrollView scroll=new ScrollView(this);root.addView(scroll,new LinearLayout.LayoutParams(-1,0,1));
        content=new LinearLayout(this);content.setOrientation(1);content.setPadding(18,18,18,24);scroll.addView(content);
        LinearLayout nav=new LinearLayout(this);nav.setPadding(8,8,8,8);nav.setBackgroundColor(dark?Color.rgb(25,35,52):Color.WHITE);root.addView(nav);
        navButton(nav,"בית",()->home());navButton(nav,"קטגוריות",()->categories());navButton(nav,"מועדפים",()->listFavorites());navButton(nav,"הגדרות",()->settings());
    }
    void navButton(LinearLayout row,String label,Runnable action){Button b=new Button(this);b.setText(label);b.setTextSize(12);b.setAllCaps(false);b.setTextColor(navy);b.setOnClickListener(v->action.run());row.addView(b,new LinearLayout.LayoutParams(0,48,1));}
    void heading(String s){TextView t=text(s,22,dark?Color.WHITE:navy,true);LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=12;content.addView(t,p);}
    void home(){
        base("הספרייה שלך");heading("ידע זמין. בכל מקום.");
        TextView sub=text("אנציקלופדיה אישית שעובדת גם ללא חיבור לאינטרנט.",14,muted,false);content.addView(sub);
        search=new EditText(this);search.setSingleLine(true);search.setTextSize(16);search.setHint("חיפוש מאמרים ומושגים…");search.setTextColor(dark?Color.WHITE:ink);search.setHintTextColor(muted);search.setPadding(16,12,16,12);search.setBackground(bg(dark?Color.rgb(36,47,66):Color.WHITE,24));LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,54);sp.topMargin=18;sp.bottomMargin=16;content.addView(search,sp);
        search.addTextChangedListener(new TextWatcher(){public void beforeTextChanged(CharSequence s,int st,int c,int a){}public void onTextChanged(CharSequence s,int st,int before,int count){query=s.toString();renderResults();}public void afterTextChanged(Editable e){}});
        heading("גלה לפי תחום"); chips();
        heading("מאמרים מומלצים"); renderResults();
        TextView info=text("המהדורה כוללת מאמרי הדגמה מקומיים. ניתן להרחיב אותה באמצעות ייבוא חבילות ידע.",12,muted,false);info.setPadding(0,22,0,0);content.addView(info);
    }
    void chips(){String[] cats={"הכול","מדע","טכנולוגיה","גאוגרפיה","היסטוריה","ביולוגיה","חלל"};HorizontalScrollView h=new HorizontalScrollView(this);LinearLayout row=new LinearLayout(this);h.addView(row);content.addView(h);for(String c:cats){Button b=new Button(this);b.setText(c);b.setAllCaps(false);b.setTextSize(12);b.setOnClickListener(v->{category=c;renderResults();});row.addView(b);}}
    void renderResults(){
        if(content==null)return;
        // Keep the top-level home controls and replace only the results section.
        int marker=-1; for(int i=0;i<content.getChildCount();i++){View v=content.getChildAt(i);if(v instanceof TextView && ((TextView)v).getText().toString().equals("מאמרים מומלצים")){marker=i;break;}}
        if(marker<0)return; while(content.getChildCount()>marker+1)content.removeViewAt(marker+1);
        int count=0; for(Article a:articles){if(!category.equals("הכול")&&!a.category.equals(category))continue;String q=query.toLowerCase(Locale.ROOT);if(!q.isEmpty()&&! (a.title+" "+a.summary+" "+a.body+" "+a.category).toLowerCase(Locale.ROOT).contains(q))continue;articleCard(a);count++;}
        if(count==0)content.addView(text("לא נמצאו תוצאות. נסו מילות חיפוש אחרות.",15,muted,false));
    }
    void articleCard(Article a){LinearLayout card=new LinearLayout(this);card.setOrientation(1);card.setPadding(16,14,16,14);card.setBackground(bg(dark?Color.rgb(31,43,62):Color.WHITE,18));LinearLayout.LayoutParams p=new LinearLayout.LayoutParams(-1,-2);p.bottomMargin=10;content.addView(card,p);
        TextView cat=text(a.category.toUpperCase(Locale.ROOT),10,gold,true);cat.setGravity(Gravity.RIGHT);card.addView(cat);
        TextView title=text(a.title,18,dark?Color.WHITE:navy,true);title.setPadding(0,5,0,5);card.addView(title);
        card.addView(text(a.summary,13,muted,false));card.setOnClickListener(v->openArticle(a));
    }
    void openArticle(Article a){history.remove(a.title);history.add(a.title);save();base(a.category);heading(a.title);TextView body=text(a.body,17,dark?Color.rgb(225,230,240):ink,false);body.setLineSpacing(10,1);body.setPadding(0,10,0,20);content.addView(body);
        Button fav=new Button(this);fav.setText(favorites.contains(a.title)?"הסר מהמועדפים":"הוסף למועדפים");fav.setAllCaps(false);fav.setOnClickListener(v->{if(!favorites.add(a.title))favorites.remove(a.title);save();openArticle(a);});content.addView(fav);
        Button share=new Button(this);share.setText("שתף מאמר");share.setAllCaps(false);share.setOnClickListener(v->{Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,a.title+"\n\n"+a.body);startActivity(Intent.createChooser(i,"שיתוף מאמר"));});content.addView(share);
        Button copy=new Button(this);copy.setText("העתק טקסט");copy.setAllCaps(false);copy.setOnClickListener(v->{((android.content.ClipboardManager)getSystemService(CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(a.title,a.body));Toast.makeText(this,"המאמר הועתק",Toast.LENGTH_SHORT).show();});content.addView(copy);
    }
    void categories(){base("קטגוריות");heading("בחרו תחום ידע");LinkedHashSet<String> cs=new LinkedHashSet<>();for(Article a:articles)cs.add(a.category);for(String c:cs){Button b=new Button(this);b.setText(c+"  ·  "+articles.stream().filter(a->a.category.equals(c)).count()+" ערכים");b.setAllCaps(false);b.setOnClickListener(v->{category=c;query="";home();});content.addView(b);}}
    void listFavorites(){base("מועדפים");heading("המאמרים השמורים");if(favorites.isEmpty())content.addView(text("עדיין לא שמרת מאמרים.",15,muted,false));for(String name:favorites)for(Article a:articles)if(a.title.equals(name))articleCard(a);}
    void settings(){base("הגדרות");heading("התאמה אישית");Button theme=new Button(this);theme.setText(dark?"מעבר למצב בהיר":"מעבר למצב כהה");theme.setAllCaps(false);theme.setOnClickListener(v->{prefs.edit().putBoolean("dark",!dark).apply();settings();});content.addView(theme);
        Button hist=new Button(this);hist.setText("היסטוריית קריאה ("+history.size()+")");hist.setAllCaps(false);hist.setOnClickListener(v->{base("היסטוריה");heading("נקראו לאחרונה");for(String n:history)for(Article a:articles)if(a.title.equals(n))articleCard(a);});content.addView(hist);
        Button clear=new Button(this);clear.setText("נקה היסטוריה");clear.setAllCaps(false);clear.setOnClickListener(v->{history.clear();save();settings();});content.addView(clear);
        Button export=new Button(this);export.setText("ייצוא רשימת מועדפים");export.setAllCaps(false);export.setOnClickListener(v->{StringBuilder s=new StringBuilder("Knowledge Vault – מועדפים\n");for(String f:favorites)s.append("• ").append(f).append("\n");Intent i=new Intent(Intent.ACTION_SEND);i.setType("text/plain");i.putExtra(Intent.EXTRA_TEXT,s.toString());startActivity(Intent.createChooser(i,"ייצוא"));});content.addView(export);
        content.addView(text("גרסה 1.0.0 · עובד ללא הרשאת אינטרנט · התוכן נשמר במכשיר",12,muted,false));
    }
}
