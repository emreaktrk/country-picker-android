package com.mukesh.countrypicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.mukesh.countrypicker.listeners.BottomSheetInteractionListener;
import com.mukesh.countrypicker.listeners.OnCountryPickerListener;
import com.mukesh.countrypicker.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class CountryPicker implements BottomSheetInteractionListener {

  // region Countries
  private final Country[] COUNTRIES = {
          new Country("AD", "Andorra", "+376", R.drawable.flag_andorra, "EUR"),
          new Country("AE", "United Arab Emirates", "+971", R.drawable.flag_united_arab_emirates, "AED"),
          new Country("AF", "Afghanistan", "+93", R.drawable.flag_afghanistan, "AFN"),
          new Country("AG", "Antigua and Barbuda", "+1", R.drawable.flag_antigua_and_barbuda, "XCD"),
          new Country("AI", "Anguilla", "+1", R.drawable.flag_anguilla, "XCD"),
          new Country("AL", "Albania", "+355", R.drawable.flag_albania, "ALL"),
          new Country("AM", "Armenia", "+374", R.drawable.flag_armenia, "AMD"),
          new Country("AO", "Angola", "+244", R.drawable.flag_angola, "AOA"),
          //      new Country("AQ", "Antarctica", "+672", R.drawable.flag_aq, "USD"),
          new Country("AR", "Argentina", "+54", R.drawable.flag_argentina, "ARS"),
          new Country("AS", "American Samoa", "+1", R.drawable.flag_samoa, "USD"),
          new Country("AT", "Austria", "+43", R.drawable.flag_austria, "EUR"),
          new Country("AU", "Australia", "+61", R.drawable.flag_australia, "AUD"),
          new Country("AW", "Aruba", "+297", R.drawable.flag_aruba, "AWG"),
          new Country("AX", "Aland Islands", "+358", R.drawable.flag_aland_islands, "EUR"),
          new Country("AZ", "Azerbaijan", "+994", R.drawable.flag_azerbaijan, "AZN"),
          new Country("BA", "Bosnia and Herzegovina", "+387", R.drawable.flag_bosnia_and_herzegovina, "BAM"),
          new Country("BB", "Barbados", "+1", R.drawable.flag_barbados, "BBD"),
          new Country("BD", "Bangladesh", "+880", R.drawable.flag_bangladesh, "BDT"),
          new Country("BE", "Belgium", "+32", R.drawable.flag_belgium, "EUR"),
          new Country("BF", "Burkina Faso", "+226", R.drawable.flag_burkina_faso, "XOF"),
          new Country("BG", "Bulgaria", "+359", R.drawable.flag_bulgaria, "BGN"),
          new Country("BH", "Bahrain", "+973", R.drawable.flag_bahrain, "BHD"),
          new Country("BI", "Burundi", "+257", R.drawable.flag_burundi, "BIF"),
          new Country("BJ", "Benin", "+229", R.drawable.flag_benin, "XOF"),
          //      new Country("BL", "Saint Barthelemy", "+590", R.drawable.flag_bl, "EUR"),
          new Country("BM", "Bermuda", "+1", R.drawable.flag_bermuda, "BMD"),
          new Country("BN", "Brunei Darussalam", "+673", R.drawable.flag_brunei, "BND"),
          new Country("BO", "Bolivia, Plurinational State of", "+591", R.drawable.flag_bolivia, "BOB"),
          new Country("BQ", "Bonaire", "+599", R.drawable.flag_bonaire, "USD"),
          new Country("BR", "Brazil", "+55", R.drawable.flag_brazil, "BRL"),
          new Country("BS", "Bahamas", "+1", R.drawable.flag_bahamas, "BSD"),
          new Country("BT", "Bhutan", "+975", R.drawable.flag_bhutan, "BTN"),
          //      new Country("BV", "Bouvet Island", "+47", R.drawable.flag_bv, "NOK"),
          new Country("BW", "Botswana", "+267", R.drawable.flag_botswana, "BWP"),
          new Country("BY", "Belarus", "+375", R.drawable.flag_belarus, "BYR"),
          new Country("BZ", "Belize", "+501", R.drawable.flag_belize, "BZD"),
          new Country("CA", "Canada", "+1", R.drawable.flag_canada, "CAD"),
          new Country("CC", "Cocos (Keeling) Islands", "+61", R.drawable.flag_cocos_island, "AUD"),
          //      new Country("CD", "Congo, The Democratic Republic of the", "+243", R.drawable.flag_cd, "CDF"),
          new Country("CF", "Central African Republic", "+236", R.drawable.flag_central_african_republic, "XAF"),
          //      new Country("CG", "Congo", "+242", R.drawable.flag_cg, "XAF"),
          new Country("CH", "Switzerland", "+41", R.drawable.flag_switzerland, "CHF"),
          //      new Country("CI", "Ivory Coast", "+225", R.drawable.flag_ci, "XOF"),
          new Country("CK", "Cook Islands", "+682", R.drawable.flag_cook_islands, "NZD"),
          new Country("CL", "Chile", "+56", R.drawable.flag_chile, "CLP"),
          new Country("CM", "Cameroon", "+237", R.drawable.flag_cameroon, "XAF"),
          new Country("CN", "China", "+86", R.drawable.flag_china, "CNY"),
          new Country("CO", "Colombia", "+57", R.drawable.flag_colombia, "COP"),
          new Country("CR", "Costa Rica", "+506", R.drawable.flag_costa_rica, "CRC"),
          new Country("CU", "Cuba", "+53", R.drawable.flag_cuba, "CUP"),
          new Country("CV", "Cape Verde", "+238", R.drawable.flag_cape_verde, "CVE"),
          new Country("CW", "Curacao", "+599", R.drawable.flag_curacao, "ANG"),
          new Country("CX", "Christmas Island", "+61", R.drawable.flag_christmas_island, "AUD"),
          new Country("CY", "Cyprus", "+357", R.drawable.flag_cyprus, "EUR"),
          new Country("CZ", "Czech Republic", "+420", R.drawable.flag_czech_republic, "CZK"),
          new Country("DE", "Germany", "+49", R.drawable.flag_germany, "EUR"),
          new Country("DJ", "Djibouti", "+253", R.drawable.flag_djibouti, "DJF"),
          new Country("DK", "Denmark", "+45", R.drawable.flag_denmark, "DKK"),
          new Country("DM", "Dominica", "+1", R.drawable.flag_dominica, "XCD"),
          new Country("DO", "Dominican Republic", "+1", R.drawable.flag_dominican_republic, "DOP"),
          new Country("DZ", "Algeria", "+213", R.drawable.flag_algeria, "DZD"),
          new Country("EC", "Ecuador", "+593", R.drawable.flag_ecuador, "USD"),
          new Country("EE", "Estonia", "+372", R.drawable.flag_estonia, "EUR"),
          new Country("EG", "Egypt", "+20", R.drawable.flag_egypt, "EGP"),
          new Country("EH", "Western Sahara", "+212", R.drawable.flag_western_sahara, "MAD"),
          new Country("ER", "Eritrea", "+291", R.drawable.flag_eritrea, "ERN"),
          new Country("ES", "Spain", "+34", R.drawable.flag_spain, "EUR"),
          new Country("ET", "Ethiopia", "+251", R.drawable.flag_ethiopia, "ETB"),
          new Country("FI", "Finland", "+358", R.drawable.flag_finland, "EUR"),
          new Country("FJ", "Fiji", "+679", R.drawable.flag_fiji, "FJD"),
          new Country("FK", "Falkland Islands (Malvinas)", "+500", R.drawable.flag_falkland_islands, "FKP"),
          new Country("FM", "Micronesia, Federated States of", "+691", R.drawable.flag_micronesia, "USD"),
          new Country("FO", "Faroe Islands", "+298", R.drawable.flag_faroe_islands, "DKK"),
          new Country("FR", "France", "+33", R.drawable.flag_france, "EUR"),
          new Country("GA", "Gabon", "+241", R.drawable.flag_gabon, "XAF"),
          new Country("GB", "United Kingdom", "+44", R.drawable.flag_united_kingdom, "GBP"),
          new Country("GD", "Grenada", "+1", R.drawable.flag_grenada, "XCD"),
          new Country("GE", "Georgia", "+995", R.drawable.flag_georgia, "GEL"),
          //      new Country("GF", "French Guiana", "+594", R.drawable.flag_gf, "EUR"),
          new Country("GG", "Guernsey", "+44", R.drawable.flag_guernsey, "GGP"),
          new Country("GH", "Ghana", "+233", R.drawable.flag_ghana, "GHS"),
          new Country("GI", "Gibraltar", "+350", R.drawable.flag_gibraltar, "GIP"),
          new Country("GL", "Greenland", "+299", R.drawable.flag_greenland, "DKK"),
          new Country("GM", "Gambia", "+220", R.drawable.flag_gambia, "GMD"),
          new Country("GN", "Guinea", "+224", R.drawable.flag_guinea, "GNF"),
          //      new Country("GP", "Guadeloupe", "+590", R.drawable.flag_gp, "EUR"),
          new Country("GQ", "Equatorial Guinea", "+240", R.drawable.flag_equatorial_guinea, "XAF"),
          new Country("GR", "Greece", "+30", R.drawable.flag_greece, "EUR"),
          //          new Country("GS", "South Georgia and the South Sandwich Islands", "+500", R.drawable.flag_gs, "GBP"),
          new Country("GT", "Guatemala", "+502", R.drawable.flag_guatemala, "GTQ"),
          new Country("GU", "Guam", "+1", R.drawable.flag_guam, "USD"),
          new Country("GW", "Guinea-Bissau", "+245", R.drawable.flag_guinea_bissau, "XOF"),
          new Country("GY", "Guyana", "+595", R.drawable.flag_guyana, "GYD"),
          new Country("HK", "Hong Kong", "+852", R.drawable.flag_hong_kong, "HKD"),
          //      new Country("HM", "Heard Island and McDonald Islands", "+000", R.drawable.flag_hm, "AUD"),
          new Country("HN", "Honduras", "+504", R.drawable.flag_honduras, "HNL"),
          new Country("HR", "Croatia", "+385", R.drawable.flag_croatia, "HRK"),
          new Country("HT", "Haiti", "+509", R.drawable.flag_haiti, "HTG"),
          new Country("HU", "Hungary", "+36", R.drawable.flag_hungary, "HUF"),
          new Country("ID", "Indonesia", "+62", R.drawable.flag_indonesia, "IDR"),
          new Country("IE", "Ireland", "+353", R.drawable.flag_ireland, "EUR"),
          new Country("IL", "Israel", "+972", R.drawable.flag_israel, "ILS"),
          new Country("IM", "Isle of Man", "+44", R.drawable.flag_isle_of_man, "GBP"),
          new Country("IN", "India", "+91", R.drawable.flag_india, "INR"),
          new Country("IO", "British Indian Ocean Territory", "+246", R.drawable.flag_british_indian_ocean_territory, "USD"),
          new Country("IQ", "Iraq", "+964", R.drawable.flag_iraq, "IQD"),
          new Country("IR", "Iran, Islamic Republic of", "+98", R.drawable.flag_iran, "IRR"),
          new Country("IS", "Iceland", "+354", R.drawable.flag_iceland, "ISK"),
          new Country("IT", "Italy", "+39", R.drawable.flag_italy, "EUR"),
          new Country("JE", "Jersey", "+44", R.drawable.flag_jersey, "JEP"),
          new Country("JM", "Jamaica", "+1", R.drawable.flag_jamaica, "JMD"),
          new Country("JO", "Jordan", "+962", R.drawable.flag_jordan, "JOD"),
          new Country("JP", "Japan", "+81", R.drawable.flag_japan, "JPY"),
          new Country("KE", "Kenya", "+254", R.drawable.flag_kenya, "KES"),
          new Country("KG", "Kyrgyzstan", "+996", R.drawable.flag_kyrgyzstan, "KGS"),
          new Country("KH", "Cambodia", "+855", R.drawable.flag_cambodia, "KHR"),
          new Country("KI", "Kiribati", "+686", R.drawable.flag_kiribati, "AUD"),
          new Country("KM", "Comoros", "+269", R.drawable.flag_comoros, "KMF"),
          new Country("KN", "Saint Kitts and Nevis", "+1", R.drawable.flag_saint_kitts_and_nevis, "XCD"),
          new Country("KP", "North Korea", "+850", R.drawable.flag_north_korea, "KPW"),
          new Country("KR", "South Korea", "+82", R.drawable.flag_south_korea, "KRW"),
          new Country("KW", "Kuwait", "+965", R.drawable.flag_kuwait, "KWD"),
          new Country("KY", "Cayman Islands", "+345", R.drawable.flag_cayman_islands, "KYD"),
          new Country("KZ", "Kazakhstan", "+7", R.drawable.flag_kazakhstan, "KZT"),
          //      new Country("LA", "Lao People's Democratic Republic", "+856", R.drawable.flag_la, "LAK"),
          new Country("LB", "Lebanon", "+961", R.drawable.flag_lebanon, "LBP"),
          new Country("LC", "Saint Lucia", "+1", R.drawable.flag_st_lucia, "XCD"),
          new Country("LI", "Liechtenstein", "+423", R.drawable.flag_liechtenstein, "CHF"),
          new Country("LK", "Sri Lanka", "+94", R.drawable.flag_sri_lanka, "LKR"),
          new Country("LR", "Liberia", "+231", R.drawable.flag_liberia, "LRD"),
          new Country("LS", "Lesotho", "+266", R.drawable.flag_lesotho, "LSL"),
          new Country("LT", "Lithuania", "+370", R.drawable.flag_lithuania, "LTL"),
          new Country("LU", "Luxembourg", "+352", R.drawable.flag_luxembourg, "EUR"),
          new Country("LV", "Latvia", "+371", R.drawable.flag_latvia, "LVL"),
          new Country("LY", "Libyan Arab Jamahiriya", "+218", R.drawable.flag_libya, "LYD"),
          new Country("MA", "Morocco", "+212", R.drawable.flag_morocco, "MAD"),
          new Country("MC", "Monaco", "+377", R.drawable.flag_monaco, "EUR"),
          new Country("MD", "Moldova, Republic of", "+373", R.drawable.flag_moldova, "MDL"),
          new Country("ME", "Montenegro", "+382", R.drawable.flag_montenegro, "EUR"),
          //      new Country("MF", "Saint Martin", "+590", R.drawable.flag_mf, "EUR"),
          new Country("MG", "Madagascar", "+261", R.drawable.flag_madagascar, "MGA"),
          new Country("MH", "Marshall Islands", "+692", R.drawable.flag_marshall_island, "USD"),
          new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389", R.drawable.flag_republic_of_macedonia, "MKD"),
          new Country("ML", "Mali", "+223", R.drawable.flag_mali, "XOF"),
          new Country("MM", "Myanmar", "+95", R.drawable.flag_myanmar, "MMK"),
          new Country("MN", "Mongolia", "+976", R.drawable.flag_mongolia, "MNT"),
          new Country("MO", "Macao", "+853", R.drawable.flag_macao, "MOP"),
          new Country("MP", "Northern Mariana Islands", "+1", R.drawable.flag_northern_marianas_islands, "USD"),
          new Country("MQ", "Martinique", "+596", R.drawable.flag_martinique, "EUR"),
          new Country("MR", "Mauritania", "+222", R.drawable.flag_mauritania, "MRO"),
          new Country("MS", "Montserrat", "+1", R.drawable.flag_montserrat, "XCD"),
          new Country("MT", "Malta", "+356", R.drawable.flag_malta, "EUR"),
          new Country("MU", "Mauritius", "+230", R.drawable.flag_mauritius, "MUR"),
          new Country("MV", "Maldives", "+960", R.drawable.flag_maldives, "MVR"),
          new Country("MW", "Malawi", "+265", R.drawable.flag_malawi, "MWK"),
          new Country("MX", "Mexico", "+52", R.drawable.flag_mexico, "MXN"),
          new Country("MY", "Malaysia", "+60", R.drawable.flag_malaysia, "MYR"),
          new Country("MZ", "Mozambique", "+258", R.drawable.flag_mozambique, "MZN"),
          new Country("NA", "Namibia", "+264", R.drawable.flag_namibia, "NAD"),
          //      new Country("NC", "New Caledonia", "+687", R.drawable.flag_nc, "XPF"),
          new Country("NE", "Niger", "+227", R.drawable.flag_niger, "XOF"),
          new Country("NF", "Norfolk Island", "+672", R.drawable.flag_norfolk_island, "AUD"),
          new Country("NG", "Nigeria", "+234", R.drawable.flag_nigeria, "NGN"),
          new Country("NI", "Nicaragua", "+505", R.drawable.flag_nicaragua, "NIO"),
          new Country("NL", "Netherlands", "+31", R.drawable.flag_netherlands, "EUR"),
          new Country("NO", "Norway", "+47", R.drawable.flag_norway, "NOK"),
          new Country("NP", "Nepal", "+977", R.drawable.flag_nepal, "NPR"),
          new Country("NR", "Nauru", "+674", R.drawable.flag_nauru, "AUD"),
          new Country("NU", "Niue", "+683", R.drawable.flag_niue, "NZD"),
          new Country("NZ", "New Zealand", "+64", R.drawable.flag_new_zealand, "NZD"),
          new Country("OM", "Oman", "+968", R.drawable.flag_oman, "OMR"),
          new Country("PA", "Panama", "+507", R.drawable.flag_panama, "PAB"),
          new Country("PE", "Peru", "+51", R.drawable.flag_peru, "PEN"),
          new Country("PF", "French Polynesia", "+689", R.drawable.flag_french_polynesia, "XPF"),
          new Country("PG", "Papua New Guinea", "+675", R.drawable.flag_papua_new_guinea, "PGK"),
          new Country("PH", "Philippines", "+63", R.drawable.flag_philippines, "PHP"),
          new Country("PK", "Pakistan", "+92", R.drawable.flag_pakistan, "PKR"),
          new Country("PL", "Poland", "+48", R.drawable.flag_republic_of_poland, "PLN"),
          //      new Country("PM", "Saint Pierre and Miquelon", "+508", R.drawable.flag_pm, "EUR"),
          new Country("PN", "Pitcairn", "+872", R.drawable.flag_pitcairn_islands, "NZD"),
          new Country("PR", "Puerto Rico", "+1", R.drawable.flag_puerto_rico, "USD"),
          new Country("PS", "Palestinian Territory, Occupied", "+970", R.drawable.flag_palestine, "ILS"),
          new Country("PT", "Portugal", "+351", R.drawable.flag_portugal, "EUR"),
          new Country("PW", "Palau", "+680", R.drawable.flag_palau, "USD"),
          new Country("PY", "Paraguay", "+595", R.drawable.flag_paraguay, "PYG"),
          new Country("QA", "Qatar", "+974", R.drawable.flag_qatar, "QAR"),
          //      new Country("RE", "Reunion", "+262", R.drawable.flag_re, "EUR"),
          new Country("RO", "Romania", "+40", R.drawable.flag_romania, "RON"),
          new Country("RS", "Serbia", "+381", R.drawable.flag_serbia, "RSD"),
          new Country("RU", "Russia", "+7", R.drawable.flag_russia, "RUB"),
          new Country("RW", "Rwanda", "+250", R.drawable.flag_rwanda, "RWF"),
          new Country("SA", "Saudi Arabia", "+966", R.drawable.flag_saudi_arabia, "SAR"),
          new Country("SB", "Solomon Islands", "+677", R.drawable.flag_solomon_islands, "SBD"),
          new Country("SC", "Seychelles", "+248", R.drawable.flag_seychelles, "SCR"),
          new Country("SD", "Sudan", "+249", R.drawable.flag_sudan, "SDG"),
          new Country("SE", "Sweden", "+46", R.drawable.flag_sweden, "SEK"),
          new Country("SG", "Singapore", "+65", R.drawable.flag_singapore, "SGD"),
          //          new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290", R.drawable.flag_sh, "SHP"),
          new Country("SI", "Slovenia", "+386", R.drawable.flag_slovakia, "EUR"),
          //      new Country("SJ", "Svalbard and Jan Mayen", "+47", R.drawable.flag_sva, "NOK"),
          new Country("SK", "Slovakia", "+421", R.drawable.flag_slovakia, "EUR"),
          new Country("SL", "Sierra Leone", "+232", R.drawable.flag_sierra_leone, "SLL"),
          new Country("SM", "San Marino", "+378", R.drawable.flag_san_marino, "EUR"),
          new Country("SN", "Senegal", "+221", R.drawable.flag_senegal, "XOF"),
          new Country("SO", "Somalia", "+252", R.drawable.flag_somalia, "SOS"),
          new Country("SR", "Suriname", "+597", R.drawable.flag_suriname, "SRD"),
          new Country("SS", "South Sudan", "+211", R.drawable.flag_south_sudan, "SSP"),
          new Country("ST", "Sao Tome and Principe", "+239", R.drawable.flag_sao_tome_and_principe, "STD"),
          new Country("SV", "El Salvador", "+503", R.drawable.flag_salvador, "SVC"),
          new Country("SX", "Sint Maarten", "+1", R.drawable.flag_sint_maarten, "ANG"),
          new Country("SY", "Syrian Arab Republic", "+963", R.drawable.flag_syria, "SYP"),
          new Country("SZ", "Swaziland", "+268", R.drawable.flag_swaziland, "SZL"),
          new Country("TC", "Turks and Caicos Islands", "+1", R.drawable.flag_turks_and_caicos, "USD"),
          new Country("TD", "Chad", "+235", R.drawable.flag_chad, "XAF"),
          //      new Country("TF", "French Southern Territories", "+262", R.drawable.flag_tf, "EUR"),
          new Country("TG", "Togo", "+228", R.drawable.flag_togo, "XOF"),
          new Country("TH", "Thailand", "+66", R.drawable.flag_thailand, "THB"),
          new Country("TJ", "Tajikistan", "+992", R.drawable.flag_tajikistan, "TJS"),
          new Country("TK", "Tokelau", "+690", R.drawable.flag_tokelau, "NZD"),
          new Country("TL", "East Timor", "+670", R.drawable.flag_east_timor, "USD"),
          new Country("TM", "Turkmenistan", "+993", R.drawable.flag_turkmenistan, "TMT"),
          new Country("TN", "Tunisia", "+216", R.drawable.flag_tunisia, "TND"),
          new Country("TO", "Tonga", "+676", R.drawable.flag_tonga, "TOP"),
          new Country("TR", "Turkey", "+90", R.drawable.flag_turkey, "TRY"),
          new Country("TT", "Trinidad and Tobago", "+1", R.drawable.flag_trinidad_and_tobago, "TTD"),
          new Country("TV", "Tuvalu", "+688", R.drawable.flag_tuvalu, "AUD"),
          new Country("TW", "Taiwan", "+886", R.drawable.flag_taiwan, "TWD"),
          new Country("TZ", "Tanzania, United Republic of", "+255", R.drawable.flag_tanzania, "TZS"),
          new Country("UA", "Ukraine", "+380", R.drawable.flag_ukraine, "UAH"),
          new Country("UG", "Uganda", "+256", R.drawable.flag_uganda, "UGX"),
          //      new Country("UM", "U.S. Minor Outlying Islands", "+1", R.drawable.flag_um, "USD"),
          new Country("US", "United States", "+1", R.drawable.flag_united_states_of_america, "USD"),
          new Country("UY", "Uruguay", "+598", R.drawable.flag_uruguay, "UYU"),
          new Country("UZ", "Uzbekistan", "+998", R.drawable.flag_uzbekistn, "UZS"),
          //      new Country("VA", "Holy See (Vatican City State)", "+379", R.drawable.flag_va, "EUR"),
          new Country("VC", "Saint Vincent and the Grenadines", "+1", R.drawable.flag_st_vincent_and_the_grenadines, "XCD"),
          new Country("VE", "Venezuela, Bolivarian Republic of", "+58", R.drawable.flag_venezuela, "VEF"),
          new Country("VG", "Virgin Islands, British", "+1", R.drawable.flag_british_virgin_islands, "USD"),
          new Country("VI", "Virgin Islands, U.S.", "+1", R.drawable.flag_virgin_islands, "USD"),
          new Country("VN", "Vietnam", "+84", R.drawable.flag_vietnam, "VND"),
          new Country("VU", "Vanuatu", "+678", R.drawable.flag_vanuatu, "VUV"),
          //      new Country("WF", "Wallis and Futuna", "+681", R.drawable.flag_wa, "XPF"),
          new Country("WS", "Samoa", "+685", R.drawable.flag_samoa, "WST"),
          new Country("XK", "Kosovo", "+383", R.drawable.flag_kosovo, "EUR"),
          new Country("YE", "Yemen", "+967", R.drawable.flag_yemen, "YER"),
          //      new Country("YT", "Mayotte", "+262", R.drawable.flag_may, "EUR"),
          new Country("ZA", "South Africa", "+27", R.drawable.flag_south_africa, "ZAR"),
          new Country("ZM", "Zambia", "+260", R.drawable.flag_zambia, "ZMW"),
          new Country("ZW", "Zimbabwe", "+263", R.drawable.flag_zimbabwe, "USD"),
  };

  public static final int SORT_BY_NONE = 0;
  public static final int SORT_BY_NAME = 1;
  public static final int SORT_BY_ISO = 2;
  public static final int SORT_BY_DIAL_CODE = 3;
  public static final int THEME_OLD = 1;
  public static final int THEME_NEW = 2;
  private int theme;

  private int style;
  private Context context;
  private int sortBy = SORT_BY_NONE;
  private OnCountryPickerListener onCountryPickerListener;
  private boolean canSearch = true;

  private List<Country> countries;
  private TextInputEditText searchEditText;
  private RecyclerView countriesRecyclerView;
  private LinearLayoutCompat rootView;
  private int textColor;
  private int hintColor;
  private int backgroundColor;
  private int searchIconId;
  private Drawable searchIcon;
  private CountriesAdapter adapter;
  private List<Country> searchResults;
  private BottomSheetDialogView bottomSheetDialog;
  private Dialog dialog;

  private CountryPicker() {
    // Empty block
  }

  private CountryPicker(Builder builder) {
    sortBy = builder.sortBy;
    if (builder.onCountryPickerListener != null) {
      onCountryPickerListener = builder.onCountryPickerListener;
    }
    style = builder.style;
    context = builder.context;
    canSearch = builder.canSearch;
    theme = builder.theme;
    countries = new ArrayList<>(Arrays.asList(COUNTRIES));
    sortCountries(countries);
  }

  private void sortCountries(@NonNull List<Country> countries) {
    if (sortBy == SORT_BY_NAME) {
      Collections.sort(countries, new Comparator<Country>() {
        @Override
        public int compare(Country country1, Country country2) {
          return country1.getName().trim().compareToIgnoreCase(country2.getName().trim());
        }
      });
    } else if (sortBy == SORT_BY_ISO) {
      Collections.sort(countries, new Comparator<Country>() {
        @Override
        public int compare(Country country1, Country country2) {
          return country1.getCode().trim().compareToIgnoreCase(country2.getCode().trim());
        }
      });
    } else if (sortBy == SORT_BY_DIAL_CODE) {
      Collections.sort(countries, new Comparator<Country>() {
        @Override
        public int compare(Country country1, Country country2) {
          return country1.getDialCode().trim().compareToIgnoreCase(country2.getDialCode().trim());
        }
      });
    }
  }

  public void showDialog(@NonNull Activity activity) {
    if (countries == null || countries.isEmpty()) {
      throw new IllegalArgumentException(context.getString(R.string.error_no_countries_found));
    } else {
      dialog = new Dialog(activity);
      View dialogView = activity.getLayoutInflater().inflate(R.layout.country_picker, null);
      initiateUi(dialogView);
      setCustomStyle(dialogView);
      setSearchEditText();
      setupRecyclerView(dialogView);
      dialog.setContentView(dialogView);
      if (dialog.getWindow() != null) {
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        if (theme == THEME_NEW) {
          Drawable background =
              ContextCompat.getDrawable(context, R.drawable.ic_dialog_new_background);
          if (background != null) {
            background.setColorFilter(
                new PorterDuffColorFilter(backgroundColor, PorterDuff.Mode.SRC_ATOP));
          }
          rootView.setBackgroundDrawable(background);
          dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
      }
      dialog.show();
    }
  }

  public void showBottomSheet(AppCompatActivity activity) {
    if (countries == null || countries.isEmpty()) {
      throw new IllegalArgumentException(context.getString(R.string.error_no_countries_found));
    } else {
      bottomSheetDialog = BottomSheetDialogView.newInstance(theme);
      bottomSheetDialog.setListener(this);
      bottomSheetDialog.show(activity.getSupportFragmentManager(), "bottomsheet");
    }
  }

  @Override public void setupRecyclerView(View sheetView) {
    searchResults = new ArrayList<>();
    searchResults.addAll(countries);
    adapter = new CountriesAdapter(sheetView.getContext(), searchResults,
        new OnItemClickListener() {
          @Override public void onItemClicked(Country country) {
            if (onCountryPickerListener != null) {
              onCountryPickerListener.onSelectCountry(country);
              if (bottomSheetDialog != null) {
                bottomSheetDialog.dismiss();
              }
              if (dialog != null) {
                dialog.dismiss();
              }
              dialog = null;
              bottomSheetDialog = null;
              textColor = 0;
              hintColor = 0;
              backgroundColor = 0;
              searchIconId = 0;
              searchIcon = null;
            }
          }
        },
        textColor);
    countriesRecyclerView.setHasFixedSize(true);
    LinearLayoutManager layoutManager = new LinearLayoutManager(sheetView.getContext());
    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    countriesRecyclerView.setLayoutManager(layoutManager);
    countriesRecyclerView.setAdapter(adapter);
  }

  @Override public void setSearchEditText() {
    if (canSearch) {
      searchEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
          // Intentionally Empty
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
          // Intentionally Empty
        }

        @Override
        public void afterTextChanged(Editable searchQuery) {
          search(searchQuery.toString());
        }
      });
      searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
        @Override public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
          InputMethodManager imm = (InputMethodManager) searchEditText.getContext()
              .getSystemService(Context.INPUT_METHOD_SERVICE);
          if (imm != null) {
            imm.hideSoftInputFromWindow(searchEditText.getWindowToken(), 0);
          }
          return true;
        }
      });
    } else {
      searchEditText.setVisibility(View.GONE);
    }
  }

  private void search(String searchQuery) {
    searchResults.clear();
    for (Country country : countries) {
      if (country.getName().toLowerCase(Locale.ENGLISH).contains(searchQuery.toLowerCase())) {
        searchResults.add(country);
      }
    }
    sortCountries(searchResults);
    adapter.notifyDataSetChanged();
  }

  @SuppressWarnings("ResourceType")
  @Override public void setCustomStyle(View sheetView) {
    if (style != 0) {
      int[] attrs =
          {
              android.R.attr.textColor, android.R.attr.textColorHint, android.R.attr.background,
              android.R.attr.drawable
          };
      TypedArray ta = sheetView.getContext().obtainStyledAttributes(style, attrs);
      textColor = ta.getColor(0, Color.BLACK);
      hintColor = ta.getColor(1, Color.GRAY);
      backgroundColor = ta.getColor(2, Color.WHITE);
      searchIconId = ta.getResourceId(3, R.drawable.ic_search);
      searchEditText.setTextColor(textColor);
      searchEditText.setHintTextColor(hintColor);
      searchIcon = ContextCompat.getDrawable(searchEditText.getContext(), searchIconId);
      if (searchIconId == R.drawable.ic_search) {
        searchIcon.setColorFilter(new PorterDuffColorFilter(hintColor, PorterDuff.Mode.SRC_ATOP));
      }
      searchEditText.setCompoundDrawablesWithIntrinsicBounds(searchIcon, null, null, null);
      rootView.setBackgroundColor(backgroundColor);
      ta.recycle();
    }
  }

  @Override public void initiateUi(View sheetView) {
    searchEditText = sheetView.findViewById(R.id.country_code_picker_search);
    countriesRecyclerView = sheetView.findViewById(R.id.countries_recycler_view);
    rootView = sheetView.findViewById(R.id.rootView);
  }

  public void setCountries(@NonNull List<Country> countries) {
    this.countries.clear();
    this.countries.addAll(countries);
    sortCountries(this.countries);
  }

  public Country getCountryFromSIM() {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    if (telephonyManager != null
        && telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
      return getCountryByISO(telephonyManager.getSimCountryIso());
    }
    return null;
  }

  public Country getCountryByLocale(@NonNull Locale locale) {
    String countryIsoCode = locale.getISO3Country().substring(0, 2).toLowerCase();
    return getCountryByISO(countryIsoCode);
  }

  public Country getCountryByName(@NonNull String countryName) {
    Collections.sort(countries, new NameComparator());
    Country country = new Country();
    country.setName(countryName);
    int i = Collections.binarySearch(countries, country, new NameComparator());
    if (i < 0) {
      return null;
    } else {
      return countries.get(i);
    }
  }

  public Country getCountryByISO(@NonNull String countryIsoCode) {
    Collections.sort(countries, new ISOCodeComparator());
    Country country = new Country();
    country.setCode(countryIsoCode);
    int i = Collections.binarySearch(countries, country, new ISOCodeComparator());
    if (i < 0) {
      return null;
    } else {
      return countries.get(i);
    }
  }

  public static class Builder {
    private Context context;
    private int sortBy = SORT_BY_NONE;
    private boolean canSearch = true;
    private OnCountryPickerListener onCountryPickerListener;
    private int style;
    private int theme = THEME_NEW;

    public Builder with(@NonNull Context context) {
      this.context = context;
      return this;
    }

    public Builder style(@NonNull @StyleRes int style) {
      this.style = style;
      return this;
    }

    public Builder sortBy(@NonNull int sortBy) {
      this.sortBy = sortBy;
      return this;
    }

    public Builder listener(@NonNull OnCountryPickerListener onCountryPickerListener) {
      this.onCountryPickerListener = onCountryPickerListener;
      return this;
    }

    public Builder canSearch(@NonNull boolean canSearch) {
      this.canSearch = canSearch;
      return this;
    }

    public Builder theme(@NonNull int theme) {
      this.theme = theme;
      return this;
    }

    public CountryPicker build() {
      return new CountryPicker(this);
    }
  }

  public static class ISOCodeComparator implements Comparator<Country> {
    @Override
    public int compare(Country country, Country nextCountry) {
      return country.getCode().compareToIgnoreCase(nextCountry.getCode());
    }
  }

  public static class NameComparator implements Comparator<Country> {
    @Override
    public int compare(Country country, Country nextCountry) {
      return country.getName().compareToIgnoreCase(nextCountry.getName());
    }
  }
}
