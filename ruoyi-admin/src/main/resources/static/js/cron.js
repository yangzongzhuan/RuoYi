$().ready(function () {
    $("#unrunBtn").click(function () {
        var str = $("#cron").val();
        var str_arr = str.split(" ");
        try {
            $("#v_second").val(str_arr[0])
            $("#v_min").val(str_arr[1])
            $("#v_hour").val(str_arr[2])
            $("#v_day").val(str_arr[3])
            $("#v_month").val(str_arr[4])
            $("#v_week").val(str_arr[5])
            $("#v_year").val(str_arr[6])
        } catch (e) {
        }
    });
    //second
    $("#sec_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_second").val("*");
            gen_cron();
        }
    });
    $("#sec_circle").click(function () {
        if ($(this).prop('checked')) {
            change_sec_circle();
        }
    });
    $("#sec_circle1").change(function () {
        if ($("#sec_circle").prop('checked')) {
            change_sec_circle();
        }
    });
    $("#sec_circle2").change(function () {
        if ($("#sec_circle").prop('checked')) {
            change_sec_circle();
        }
    });
    $("#sec_per").click(function () {
        if ($(this).prop('checked')) {
            change_sec_per();
        }
    });
    $("#sec_per1").change(function () {
        if ($("#sec_per").prop('checked')) {
            change_sec_per();
        }
    });
    $("#sec_per2").change(function () {
        if ($("#sec_per").prop('checked')) {
            change_sec_per();
        }
    });
    $("#sec_assign").click(function () {
        if ($("#sec_assign").prop('checked')) {
            change_sec_assign();
        }
    });
    $("input[name='zd_second']").click(function () {
        if ($("#sec_assign").prop('checked')) {
            change_sec_assign();
        }
    });

    //minute
    $("#min_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_min").val("*");
            gen_cron();
        }
    });
    $("#min_circle").click(function () {
        if ($(this).prop('checked')) {
            change_min_circle();
        }
    });
    $("#min_circle1").change(function () {
        if ($("#min_circle").prop('checked')) {
            change_min_circle();
        }
    });
    $("#min_circle2").change(function () {
        if ($("#min_circle").prop('checked')) {
            change_min_circle();
        }
    });
    $("#min_per").click(function () {
        if ($(this).prop('checked')) {
            change_min_per();
        }
    });
    $("#min_per1").change(function () {
        if ($("#min_per").prop('checked')) {
            change_min_per();
        }
    });
    $("#min_per2").change(function () {
        if ($("#min_per").prop('checked')) {
            change_min_per();
        }
    });
    $("#min_assign").click(function () {
        if ($("#min_assign").prop('checked')) {
            change_min_assign();
        }
    });
    $("input[name='zd_minute']").click(function () {
        if ($("#min_assign").prop('checked')) {
            change_min_assign();
        }
    });

    //hour
    $("#hour_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_hour").val("*");
            gen_cron();
        }
    });
    $("#hour_circle").click(function () {
        if ($(this).prop('checked')) {
            change_hour_circle();
        }
    });
    $("#hour_circle1").change(function () {
        if ($("#hour_circle").prop('checked')) {
            change_hour_circle();
        }
    });
    $("#hour_circle2").change(function () {
        if ($("#hour_circle").prop('checked')) {
            change_hour_circle();
        }
    });
    $("#hour_per").click(function () {
        if ($(this).prop('checked')) {
            change_hour_per();
        }
    });
    $("#hour_per1").change(function () {
        if ($("#hour_per").prop('checked')) {
            change_hour_per();
        }
    });
    $("#hour_per2").change(function () {
        if ($("#hour_per").prop('checked')) {
            change_hour_per();
        }
    });
    $("#hour_assign").click(function () {
        if ($("#hour_assign").prop('checked')) {
            change_hour_assign();
        }
    });
    $("input[name='zd_hour']").click(function () {
        if ($("#hour_assign").prop('checked')) {
            change_hour_assign();
        }
    });

    //day
    $("#day_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_day").val("*");
            gen_cron();
        }
    });
    $("#day_no").click(function () {
        if ($(this).prop('checked')) {
            $("#v_day").val("?");
            gen_cron();
        }
    });
    $("#day_last").click(function () {
        if ($(this).prop('checked')) {
            $("#v_day").val("L");
            gen_cron();
        }
    });
    $("#day_circle").click(function () {
        if ($(this).prop('checked')) {
            change_day_circle();
        }
    });
    $("#day_circle1").change(function () {
        if ($("#day_circle").prop('checked')) {
            change_day_circle();
        }
    });
    $("#day_circle2").change(function () {
        if ($("#day_circle").prop('checked')) {
            change_day_circle();
        }
    });
    $("#day_per").click(function () {
        if ($(this).prop('checked')) {
            change_day_per();
        }
    });
    $("#day_per1").change(function () {
        if ($("#day_per").prop('checked')) {
            change_day_per();
        }
    });
    $("#day_per2").change(function () {
        if ($("#day_per").prop('checked')) {
            change_day_per();
        }
    });
    $("#day_work").click(function () {
        if ($("#day_work").prop('checked')) {
            change_day_work();
        }
    });
    $("#day_work1").change(function () {
        if ($("#day_work").prop('checked')) {
            change_day_work();
        }
    });
    $("#day_assign").click(function () {
        if ($("#day_assign").prop('checked')) {
            change_day_assign();
        }
    });
    $("input[name='zd_day']").click(function () {
        if ($("#day_assign").prop('checked')) {
            change_day_assign();
        }
    });

    //month
    $("#month_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_month").val("*");
            gen_cron();
        }
    });
    $("#month_no").click(function () {
        if ($(this).prop('checked')) {
            $("#v_month").val("?");
            gen_cron();
        }
    });
    $("#month_last").click(function () {
        if ($(this).prop('checked')) {
            $("#v_month").val("L");
            gen_cron();
        }
    });
    $("#month_circle").click(function () {
        if ($(this).prop('checked')) {
            change_month_circle();
        }
    });
    $("#month_circle1").change(function () {
        if ($("#month_circle").prop('checked')) {
            change_month_circle();
        }
    });
    $("#month_circle2").change(function () {
        if ($("#month_circle").prop('checked')) {
            change_month_circle();
        }
    });
    $("#month_per").click(function () {
        if ($(this).prop('checked')) {
            change_month_per();
        }
    });
    $("#month_per1").change(function () {
        if ($("#month_per").prop('checked')) {
            change_month_per();
        }
    });
    $("#month_per2").change(function () {
        if ($("#month_per").prop('checked')) {
            change_month_per();
        }
    });
    $("#month_assign").click(function () {
        if ($("#month_assign").prop('checked')) {
            change_month_assign();
        }
    });
    $("input[name='zd_month']").click(function () {
        if ($("#month_assign").prop('checked')) {
            change_month_assign();
        }
    });
    
    //week
    $("#week_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_week").val("*");
            gen_cron();
        }
    });
    $("#week_no").click(function () {
        if ($(this).prop('checked')) {
            $("#v_week").val("?");
            gen_cron();
        }
    });
    $("#week_circle").click(function () {
        if ($(this).prop('checked')) {
            change_week_circle();
        }
    });
    $("#week_circle1").change(function () {
        if ($("#week_circle").prop('checked')) {
            change_week_circle();
        }
    });
    $("#week_circle2").change(function () {
        if ($("#week_circle").prop('checked')) {
            change_week_circle();
        }
    });
    $("#week_last").click(function () {
        if ($("#week_last").prop('checked')) {
            change_week_last();
        }
    });
    $("#week_last1").change(function () {
        if ($("#week_last").prop('checked')) {
            change_week_last();
        }
    });
    $("#week_num").click(function () {
        if ($("#week_num").prop('checked')) {
            change_week_num();
        }
    });
    $("#week_num1").change(function () {
        if ($("#week_num").prop('checked')) {
            change_week_num();
        }
    });
    $("#week_num2").change(function () {
        if ($("#week_num").prop('checked')) {
            change_week_num();
        }
    });
    $("#week_assign").click(function () {
        if ($("#week_assign").prop('checked')) {
            change_week_assign();
        }
    });
    $("input[name='zd_week']").click(function () {
        if ($("#week_assign").prop('checked')) {
            change_week_assign();
        }
    });
    
    //year
    $("#year_all").click(function () {
        if ($(this).prop('checked')) {
            $("#v_year").val("*");
            gen_cron();
        }
    });
    $("#year_no").click(function () {
        if ($(this).prop('checked')) {
            $("#v_year").val("");
            gen_cron();
        }
    });
    $("#year_circle").click(function () {
        if ($(this).prop('checked')) {
            change_year_circle();
        }
    });
    $("#year_circle1").change(function () {
        if ($("#year_circle").prop('checked')) {
            change_year_circle();
        }
    });
    $("#year_circle2").change(function () {
        if ($("#year_circle").prop('checked')) {
            change_year_circle();
        }
    });
});

function change_sec_assign() {
    var sec_array = new Array();
    $("input[name='zd_second']:checked").each(function () {
        sec_array[sec_array.length] = $(this).val();
    });
    sec_array = sec_array.join(",");
    if (sec_array == null || sec_array == '') {
        $("#v_second").val("*");
    } else {
        $("#v_second").val(sec_array);
    }
    gen_cron();
}

function change_sec_circle() {
    var v1 = $("#sec_circle1").val();
    var v2 = $("#sec_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#sec_circle1").val(v1);
    }
    if (v2 < 2) {
        v2 = 2;
        $("#sec_circle2").val(v2);
    }
    if (v2 > 59) {
        v2 = 59;
        $("#sec_circle2").val(v2);
    }
    if (v1 > 59) {
        v1 = 59;
        $("#sec_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#sec_circle1").val(v1);
    }
    if (v1 > 60 || v2 > 60) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_second").val(v1 + "-" + v2);
    gen_cron();
}

function change_sec_per() {
    var v1 = $("#sec_per1").val();
    var v2 = $("#sec_per2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 0) {
        v1 = 0;
        $("#sec_per1").val(v1);
    }
    if (v1 > 59) {
        v1 = 59;
        $("#sec_per1").val(v1);
    }
    if (v2 < 1) {
        v2 = 1;
        $("#sec_per2").val(v2);
    }
    if (v2 > 59) {
        v2 = 59;
        $("#sec_per2").val(v2);
    }
    $("#v_second").val(v1 + "/" + v2);
    gen_cron()
}

function change_min_assign() {
    var sec_array = new Array();
    $("input[name='zd_minute']:checked").each(function () {
        sec_array[sec_array.length] = $(this).val();
    });
    sec_array = sec_array.join(",");
    if (sec_array == null || sec_array == '') {
        $("#v_min").val("*");
    } else {
        $("#v_min").val(sec_array);
    }
    gen_cron();
}

function change_min_circle() {
    var v1 = $("#min_circle1").val();
    var v2 = $("#min_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#min_circle1").val(v1);
    }
    if (v2 < 2) {
        v2 = 2;
        $("#min_circle2").val(v2);
    }
    if (v2 > 59) {
        v2 = 59;
        $("#min_circle2").val(v2);
    }
    if (v1 > 59) {
        v1 = 59;
        $("#min_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#min_circle1").val(v1);
    }
    if (v1 > 60 || v2 > 60) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_min").val(v1 + "-" + v2);
    gen_cron();
}

function change_min_per() {
    var v1 = $("#min_per1").val();
    var v2 = $("#min_per2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 0) {
        v1 = 0;
        $("#min_per1").val(v1);
    }
    if (v1 > 59) {
        v1 = 59;
        $("#min_per1").val(v1);
    }
    if (v2 < 1) {
        v2 = 1;
        $("#min_per2").val(v2);
    }
    if (v2 > 59) {
        v2 = 59;
        $("#min_per2").val(v2);
    }

    $("#v_min").val(v1 + "/" + v2);
    gen_cron()
}

function change_hour_assign() {
    var sec_array = new Array();
    $("input[name='zd_hour']:checked").each(function () {
        sec_array[sec_array.length] = $(this).val();
    });
    sec_array = sec_array.join(",");
    if (sec_array == null || sec_array == '') {
        $("#v_hour").val("*");
    } else {
        $("#v_hour").val(sec_array);
    }
    gen_cron();
}

function change_hour_circle() {
    var v1 = $("#hour_circle1").val();
    var v2 = $("#hour_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#hour_circle1").val(v1);
    }
    if (v2 < 2) {
        v2 = 2;
        $("#hour_circle2").val(v2);
    }
    if (v2 > 23) {
        v2 = 23;
        $("#hour_circle2").val(v2);
    }
    if (v1 > 23) {
        v1 = 23;
        $("#hour_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#hour_circle1").val(v1);
    }
    if (v1 > 23 || v2 > 23) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_hour").val(v1 + "-" + v2);
    gen_cron();
}

function change_hour_per() {
    var v1 = $("#hour_per1").val();
    var v2 = $("#hour_per2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 0) {
        v1 = 0;
        $("#hour_per1").val(v1);
    }
    if (v1 > 23) {
        v1 = 23;
        $("#hour_per1").val(v1);
    }
    if (v2 < 1) {
        v2 = 1;
        $("#hour_per2").val(v2);
    }
    if (v2 > 23) {
        v2 = 23;
        $("#hour_per2").val(v2);
    }

    $("#v_hour").val(v1 + "/" + v2);
    gen_cron()
}

function change_day_assign() {
    var sec_array = new Array();
    $("input[name='zd_day']:checked").each(function () {
        sec_array[sec_array.length] = $(this).val();
    });
    sec_array = sec_array.join(",");
    if (sec_array == null || sec_array == '') {
        $("#v_day").val("*");
    } else {
        $("#v_day").val(sec_array);
    }
    gen_cron();
}

function change_day_circle() {
    var v1 = $("#day_circle1").val();
    var v2 = $("#day_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#day_circle1").val(v1);
    }
    if (v2 < 2) {
        v2 = 2;
        $("#day_circle2").val(v2);
    }
    if (v2 > 31) {
        v2 = 31;
        $("#day_circle2").val(v2);
    }
    if (v1 > 31) {
        v1 = 31;
        $("#day_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#day_circle1").val(v1);
    }
    if (v1 > 31 || v2 > 31) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_day").val(v1 + "-" + v2);
    gen_cron();
}

function change_day_per() {
    var v1 = $("#day_per1").val();
    var v2 = $("#day_per2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#day_per1").val(v1);
    }
    if (v1 > 31) {
        v1 = 31;
        $("#day_per1").val(v1);
    }
    if (v2 < 1) {
        v2 = 1;
        $("#day_per2").val(v2);
    }
    if (v2 > 31) {
        v2 = 31;
        $("#day_per2").val(v2);
    }

    $("#v_day").val(v1 + "/" + v2);
    gen_cron()
}

function change_day_work() {
    var v1 = $("#day_work1").val();

    if (v1 == null || v1 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#day_work1").val(v1);
    }
    if (v1 > 31) {
        v1 = 31;
        $("#day_work1").val(v1);
    }
    $("#v_day").val(v1 + "W");
    gen_cron()
}

function change_month_assign() {
    var sec_array = new Array();
    $("input[name='zd_month']:checked").each(function () {
        sec_array[sec_array.length] = $(this).val();
    });
    sec_array = sec_array.join(",");
    if (sec_array == null || sec_array == '') {
        $("#v_month").val("*");
    } else {
        $("#v_month").val(sec_array);
    }
    gen_cron();
}

function change_month_circle() {
    var v1 = $("#month_circle1").val();
    var v2 = $("#month_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#month_circle1").val(v1);
    }
    if (v2 < 2) {
        v2 = 2;
        $("#month_circle2").val(v2);
    }
    if (v2 > 12) {
        v2 = 12;
        $("#month_circle2").val(v2);
    }
    if (v1 > 12) {
        v1 = 12;
        $("#month_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#month_circle1").val(v1);
    }
    if (v1 > 12 || v2 > 12) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_month").val(v1 + "-" + v2);
    gen_cron();
}

function change_month_per() {
    var v1 = $("#month_per1").val();
    var v2 = $("#month_per2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#month_per1").val(v1);
    }
    if (v1 > 12) {
        v1 = 12;
        $("#month_per1").val(v1);
    }
    if (v2 < 1) {
        v2 = 1;
        $("#month_per2").val(v2);
    }
    if (v2 > 12) {
        v2 = 12;
        $("#month_per2").val(v2);
    }

    $("#v_month").val(v1 + "/" + v2);
    gen_cron()
}

function change_week_assign() {
    var sec_array = new Array();
    $("input[name='zd_week']:checked").each(function () {
        sec_array[sec_array.length] = $(this).val();
    });
    sec_array = sec_array.join(",");
    if (sec_array == null || sec_array == '') {
        $("#v_week").val("*");
    } else {
        $("#v_week").val(sec_array);
    }
    gen_cron();
}

function change_week_circle() {
    var v1 = $("#week_circle1").val();
    var v2 = $("#week_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#week_circle1").val(v1);
    }
    if (v2 < 2) {
        v2 = 2;
        $("#week_circle2").val(v2);
    }
    if (v2 > 7) {
        v2 = 7;
        $("#week_circle2").val(v2);
    }
    if (v1 > 7) {
        v1 = 7;
        $("#week_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#week_circle1").val(v1);
    }
    if (v1 > 7 || v2 > 7) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_week").val(v1 + "-" + v2);
    gen_cron();
}

function change_week_last() {
    var v1 = $("#week_last1").val();

    if (v1 == null || v1 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#week_last1").val(v1);
    }
    if (v1 > 7) {
        v1 = 7;
        $("#week_last1").val(v1);
    }
    $("#v_week").val(v1 + "L");
    gen_cron()
}

function change_week_num() {
    var v1 = $("#week_num1").val();
    var v2 = $("#week_num2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 1) {
        v1 = 1;
        $("#week_num1").val(v1);
    }
    if (v2 < 1) {
        v2 = 1;
        $("#week_num2").val(v2);
    }
    if (v2 > 7) {
        v2 = 7;
        $("#week_num2").val(v2);
    }
    if (v1 > 4) {
        v1 = 4;
        $("#week_num1").val(v1);
    }

    $("#v_week").val(v1 + "#" + v2);
    gen_cron();
}

function change_year_circle() {
    var v1 = $("#year_circle1").val();
    var v2 = $("#year_circle2").val();
    if (v1 == null || v1 == '' || v2 == null || v2 == '') {
        return;
    }
    if (v1 < 2000) {
        v1 = 2000;
        $("#year_circle1").val(v1);
    }
    if (v2 < 2000) {
        v2 = 2000;
        $("#year_circle2").val(v2);
    }
    if (v2 > 3000) {
        v2 = 3000;
        $("#year_circle2").val(v2);
    }
    if (v1 > 3000) {
        v1 = 3000;
        $("#year_circle1").val(v1);
    }
    if (v1 >= v2) {
        v1 = v2 - 1;
        $("#year_circle1").val(v1);
    }
    if (v1 > 3000 || v2 > 3000) {
        return;
    }
    if (v1 > v2) {
        return;
    }
    $("#v_year").val(v1 + "-" + v2);
    gen_cron();
}

function gen_cron() {
    var str = $("#v_second").val() + " "
        + $("#v_min").val() + " "
        + $("#v_hour").val() + " "
        + $("#v_day").val() + " "
        + $("#v_month").val() + " "
        + $("#v_week").val() + " "
        + $("#v_year").val();
    $("#cron").val(str);
}