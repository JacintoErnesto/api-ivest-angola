package ucan.edu.api_sig_invest_angola.utils;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.apache.commons.lang3.text.WordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.*;
import java.security.*;
import java.text.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public final class Utils {
    //private static final MyLogger LOGGER = MyLogger.getInstance(Utils.class);

    // Para teste
    public static String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0ZWxlZm9uZSI6IjkzMjYzMjg5NyIsInByaW1laXJvQWNlc3NvIjpmYWxzZSwiaXNzIjoiQVBJIExFTSBmcm9udCBlbmQiLCJuaWYiOiIwMDM4MDUzNTJMQTAzMCIsImlkIjoxMDAsImZ1bmNpb25hcmlvIjpmYWxzZSwic2V4byI6Ik0iLCJleHAiOjE5MDYwODk1MzgsImVtYWlsIjoiZXJuZXN0by5zYW1ib25nb0BvdXRsb29rLmNvbSIsInBlcmZpbCI6WyJBR1RfQ09OU1VMVEEiLCJBR1RfRElSRUNBTyJdfQ.WmdTtfTRiP38zzT1ie-9ODVK-sBI7pK534zQwzgmBsw";

    /**
     * constructor private
     */
    private Utils() {
    }

    /**
     * Validar Objecto
     *
     * @param object {@link Object}.
     * @return <code>SET</code> indicando a verifica??o.
     */
    @SuppressWarnings("rawtypes")
    public static Set validate(Object object) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        return validator.validate(object);
    }

    public static boolean isNullOrZero(BigDecimal value) {
        return value == null || value.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }

    public static boolean isDuplicate(List<String> list) {
        for (String element : list) {
            if (Collections.frequency(list, element) > 2)
                return true;
        }
        return false;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static boolean isUpdate(Long id) {
        return id != null && id > 0;
    }

    public static Page<?> toPage(List<?> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        if (start > list.size())
            return new PageImpl<>(new ArrayList<>(), pageable, list.size());
        return new PageImpl<>(list.subList(start, end), pageable, list.size());
    }

    public static <T> Stream<T> nullableListToStream(List<T> list) {
        return list == null ? Stream.empty() : list.stream();
    }

    public static <T> Stream<T> nullablePageToStream(Page<T> page) {
        return page == null ? Stream.empty() : page.getContent().stream();
    }

    /**
     * Verifica se atributo ? nulo ou vazio(String).
     *
     * @param atributo {@link Object}.
     * @return <code>boolean</code> indicando a verifica??o.
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object atributo) {
        if (atributo != null) {
            if (atributo instanceof String && ((String) atributo).trim().isEmpty()) {
                return true;
            } else if (atributo instanceof Collection) {
                return ((Collection) atributo).isEmpty();
            } else if (atributo instanceof Map) {
                return ((Map) atributo).isEmpty();
            } else if (atributo.getClass().isArray()) {
                return Array.getLength(atributo) < 1;
            } else if (atributo instanceof Number) {
                return false;
            } else if (atributo instanceof Date) {
                return false;
            }
            return false;
        }
        return true;
    }

    /**
     * Verifica se uma String e nula
     *
     * @param str
     * @return
     */
    public static Boolean isStringEmpty(String str) {
        if (null != str) {
            char[] array = str.toCharArray();
            for (char c : array) {
                if (!Character.isWhitespace(c)) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    public static String get3LetterDayOfTheWeek(String week) {
        return week.substring(0, 3);
    }

    public static boolean isString(String word) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    public static boolean isNumber(String word) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(word);
        return matcher.matches();
    }

    public static String converterMesIntToString(int mes) {
        return switch (mes) {
            case 1 -> "JAN";
            case 2 -> "FEV";
            case 3 -> "MAR";
            case 4 -> "APR";
            case 5 -> "MAY";
            case 6 -> "JUN";
            case 7 -> "JUL";
            case 8 -> "AUG";
            case 9 -> "SEP";
            case 10 -> "OCT";
            case 11 -> "NOV";
            case 12 -> "DEC";
            default -> null;
        };
    }

    public static String converterMesIntToExtenso(int mes) {
        return switch (mes) {
            case 1 -> "Janeiro";
            case 2 -> "Fevereiro";
            case 3 -> "Março";
            case 4 -> "Abril";
            case 5 -> "Maio";
            case 6 -> "Junho";
            case 7 -> "Julho";
            case 8 -> "Agosto";
            case 9 -> "Setembro";
            case 10 -> "Outubro";
            case 11 -> "Novembro";
            case 12 -> "Dezembro";
            default -> null;
        };
    }

    /**
     * Gerador de senhas.
     */
    public static String gerarSenha() {
        UUID uuid = UUID.randomUUID();
        String myRandom = uuid.toString();
        return myRandom.substring(0, 6);
    }

    public static Boolean isNumeric(String conteudo) {
        boolean condicao = Boolean.TRUE;

        if (conteudo != null && !conteudo.trim().isEmpty()) {
            for (int i = 0; i < conteudo.length(); i++) {
                char caracterParametro = conteudo.charAt(i);

                if (!Character.isDigit(caracterParametro)) {
                    condicao = Boolean.FALSE;
                    break;
                }
            }
        } else {
            condicao = Boolean.FALSE;
        }
        return condicao;
    }

    public static String getIp() throws UnknownHostException {
        InetAddress IP = InetAddress.getLocalHost();
        return IP.getHostAddress();
    }

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll(
                "[^\\p{ASCII}]",
                ""
        );
    }

    /**
     * getAllFiledName
     *
     * @param obj
     * @return
     */
    private static List<String> getAllFiledName(Object obj) {
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        List<String> list = new ArrayList<>(declaredFields.length);
        for (Field field : declaredFields) {
            list.add(field.getName());
        }
        return list;
    }

    public static int getTwoDigitYear() {
        Calendar data = Calendar.getInstance();
        data.setTime(new Date());
        return (data.get(Calendar.YEAR) % 100);
    }

    public static String getTwoDigitMonth() {
        return String.format("%02d", LocalDate.now().getMonth().getValue());
    }

    public static BigDecimal log(int base_int, BigDecimal x) {
        BigDecimal result = BigDecimal.ZERO;

        BigDecimal input = new BigDecimal(x.toString());
        int decimalPlaces = 100;
        int scale = input.precision() + decimalPlaces;

        int maxite = 10000;
        int ite = 0;
        BigDecimal maxError_BigDecimal = new BigDecimal(BigInteger.ONE,
                decimalPlaces + 1);
        // System.out.println("maxError_BigDecimal " + maxError_BigDecimal);
        // System.out.println("scale " + scale);

        RoundingMode a_RoundingMode = RoundingMode.UP;

        BigDecimal two_BigDecimal = new BigDecimal("2");
        BigDecimal base_BigDecimal = new BigDecimal(base_int);

        while (input.compareTo(base_BigDecimal) == 1) {
            result = result.add(BigDecimal.ONE);
            input = input.divide(base_BigDecimal, scale, a_RoundingMode);
        }

        BigDecimal fraction = new BigDecimal("0.5");
        input = input.multiply(input);
        BigDecimal resultplusfraction = result.add(fraction);
        while (((resultplusfraction).compareTo(result) == 1)
                && (input.compareTo(BigDecimal.ONE) == 1)) {
            if (input.compareTo(base_BigDecimal) == 1) {
                input = input
                        .divide(base_BigDecimal, scale, a_RoundingMode);
                result = result.add(fraction);
            }
            input = input.multiply(input);
            fraction = fraction.divide(two_BigDecimal, scale,
                    a_RoundingMode);
            resultplusfraction = result.add(fraction);
            if (fraction.abs().compareTo(maxError_BigDecimal) == -1) {
                break;
            }
            if (maxite == ite) {
                break;
            }
            ite++;
        }

        MathContext a_MathContext = new MathContext(
                ((decimalPlaces - 1) + (result.precision() - result.scale())),
                RoundingMode.HALF_UP);
        BigDecimal roundedResult = result.round(a_MathContext);
        BigDecimal strippedRoundedResult = roundedResult
                .stripTrailingZeros();
        //return result;
        //return result.round(a_MathContext);
        return strippedRoundedResult;
    }

    public static int retornaNumeroFormulario(String referencia) {
        String valor = "";
        valor = referencia.substring(8);
        return Integer.parseInt(valor);
    }

    public static String formatarValor(Double price, Locale locale) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
        formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(locale));
        return formatter.format(price);
    }

    public static String formatarQuantidade(Double price, Locale locale) {
        NumberFormat formatter = NumberFormat.getNumberInstance(locale);
        return formatter.format(price);
    }

    public static String formatarValorCambio(Double price, Locale locale) {
        DecimalFormat formatter = new DecimalFormat("###,###,##0.000");
        formatter.setDecimalFormatSymbols(new DecimalFormatSymbols(locale));
        return formatter.format(price);
    }

    public static Double removeVirgula(String valor) {
        valor = valor.replaceAll(",", ".");
        return Double.parseDouble(valor);
    }

    public static String formatarValorMoeda(int valor) {
        if (valor > 0) {
            DecimalFormat mask = new DecimalFormat("###,###,###.00");
            return mask.format(valor);
        } else {
            return "0";
        }
    }

    public static String capitalize(String aCapitalizar) {
        return !Utils.isEmpty(aCapitalizar) ? WordUtils.capitalize(
                aCapitalizar.toLowerCase()
        ) : null;
    }

    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 01-06-2020
     *
     */
    public static void copyObjecto(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static void copyObjectoExclude(Object source, Object target, String[] params) {
        BeanUtils.copyProperties(source, target, params);
    }

    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 14-06-2020
     *
     */
    public static LocalDateTime dateParaLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static LocalDate dateParaLocalDateNormal(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 22-07-2020
     *
     */
    public static long diferencaEntreDatas(Date data1, Date data2) {
        return Duration.between(data1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), data2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()).toDays();
    }

    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 14-06-2020
     *
     */
    public static Date deLocalDateTimeParaDate(LocalDateTime dateToConvert) {
        return Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    public static Date deLocalDateParaDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static boolean dataMaiorOuIgual(LocalDate data1, LocalDate data2) {
        return Utils.deLocalDateParaDate(data1).getTime() >= Utils.deLocalDateParaDate(data2).getTime();
    }

    public static boolean data1MaiorQueData2(LocalDate data1, LocalDate data2) {
        return Utils.deLocalDateParaDate(data1).getTime() > Utils.deLocalDateParaDate(data2).getTime();
    }

    public static boolean dataMenorOuIgual(LocalDate data1, LocalDate data2) {
        return Utils.deLocalDateParaDate(data1).getTime() <= Utils.deLocalDateParaDate(data2).getTime();
    }

    public static boolean dataMenor(LocalDate data1, LocalDate data2) {
        return Utils.deLocalDateParaDate(data1).getTime() < Utils.deLocalDateParaDate(data2).getTime();
    }
    //Utils.deLocalDateParaDate(dataActual).getTime()>=Utils.deLocalDateParaDate(dataAberturaPeriodo).getTime();

    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 14-06-2020
     *
     */
    public static Date deStringParaData(String data) throws ParseException {
        SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return formatar.parse(data);
    }
    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 14-06-2020
     *
     */


    public static Date deStringParaDataSemHora(String data) throws ParseException {
        SimpleDateFormat formatar = new SimpleDateFormat("yyyy-MM-dd");
        return formatar.parse(data);
    }

    public static Date deStringParaDataII(String data) throws ParseException {
        //SimpleDateFormat formatar = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        SimpleDateFormat formatar = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return formatar.parse(data);
    }

    /*
     * NOME: ERNESTO TADEU TCHITECULO SAMBONGO
     * DATA: 29-06-2020
     *
     */
    public static Date adicionaDiasDate(Date data, Integer dias) {
        return deLocalDateTimeParaDate(dateParaLocalDate(data).plusDays(dias));
    }

    public static String formataDataComecoAnoFimDia(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    //DIFERENÇA ENTRE DUAS DATAS - LOCALDATE
    public static boolean dataMaiorIgual(LocalDate data1, LocalDate data2) {
        if (data1.isAfter(data2))
            return true;
        return !data1.isBefore(data2);
    }

    public static LocalDate deStringParaLocalDate(String data) {
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate deStringParaLocalDateForTime(String data) {
        return LocalDate.parse(data, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
    }

    public static boolean dataMenorIgual(LocalDate data1, LocalDate data2) {
        if (data1.isBefore(data2))
            return true;
        return !data1.isAfter(data2);
    }

    public static String generateNumeroLiquidacao(Long ano, long numero) {
        return ano + "." + String.format("%010d", numero);
    }

    public static String getLastWordAfterDot(String word) {
        return word.replaceAll("^.*?(\\w+)\\W*$", "$1");
    }


    public static boolean verifyIfExistInEnum(Class<? extends Enum<?>> enumClass, String texto) {

        String[] names = getNames(enumClass);
        return Arrays.asList(names).contains(texto.toUpperCase());
    }

    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static boolean contains(String[] seccoes, String seccao) {
        if (seccoes != null) {
            return Arrays.asList(seccoes).contains(seccao);
        } else {
            return false;
        }
    }

    public static int daysBetweenDates(LocalDate dt1, LocalDate dt2) {
        long diffDays = ChronoUnit.DAYS.between(dt1, dt2);
        return Math.abs((int) diffDays);
    }

    public static LocalDate subtractDaysSkippingWeekends(LocalDate date, int days) {
        LocalDate result = date;
        int subtractedDays = 0;
        while (subtractedDays < days) {
            result = result.minusDays(1);
            if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY || result.getDayOfWeek() == DayOfWeek.SUNDAY)) {
                ++subtractedDays;
            }
        }
        return result;
    }

    public static LocalDate subtractDays(LocalDate date, int days) {
        LocalDate result = date;
        int subtractedDays = 0;
        while (subtractedDays < days) {
            result = result.minusDays(1);
            ++subtractedDays;
        }
        return result;
    }

    public static LocalDateTime subtractDays(LocalDateTime date, int days) {
        LocalDateTime result = date;
        int subtractedDays = 0;
        while (subtractedDays < days) {
            result = result.minusDays(1);
            ++subtractedDays;
        }
        return result;
    }

    //VALIDAR NUMERO DE TELEFONE
    public boolean verifyPhoneNumber(String str) {
        if (str == null || str.trim().isEmpty())
            return false;

        str = str.trim().toLowerCase();

        return !str.contains("n/a") && !str.contains("na") && !str.startsWith("22") && !str.contains(" ");
    }

    //DATA MEIO PARA DOUDÉCIMO
    public static LocalDate dataMeioExercicio(Integer exercicio, Integer mes) {
        return LocalDate.of(exercicio, mes, 30);
    }

    //DATA QUE INICIO O PERIODO DE COBRANÇA DAS MULTAS.
    public static LocalDate dataAberturaPeriodo(Integer exercicio, Integer mes) {
        return LocalDate.of(exercicio + 1, mes, 01);
    }

    public static long diferencaDiasParaMultaIP(LocalDate dataActual, LocalDate dataFechoPeriodoIP) {
        //VAMOS FAZER A DIFERENÇA DE DIAS ENTRE A DATA_ATUAL E DATA_COMECO_MULTA
        long diferenca = ChronoUnit.DAYS.between(dataFechoPeriodoIP, dataActual);
        return diferenca >= 0 ? diferenca : 0;
    }

    public static LocalDate dataEncerramentoPeriodo(Integer exercicio, Integer mes) {
        return LocalDate.of(exercicio + 1, mes + 1, 1);
    }

    public static void main(String[] args) {
        LocalDate dataInicio = LocalDate.of(2023, Month.JULY, 1);
        LocalDate dataFim = LocalDate.of(2024, Month.MARCH, 1);
        int ano = 2023;

    }





}
