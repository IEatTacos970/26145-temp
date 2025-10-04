package org.firstinspires.ftc.teamcode.opmode.teleop;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "MainTeleOp2", group = "!")
public class MainTeleOp2 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // drivebase
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("leftFront");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("leftBack");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("rightFront");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("rightBack");

        double frontLeftPower = 0;
        double frontRightPower = 0;
        double backLeftPower = 0;
        double backRightPower = 0;

        // turret
        DcMotor turretAccel = hardwareMap.dcMotor.get("turretAccelerator");

        double turretAccelPower = 0;

        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(BRAKE);
        backLeftMotor.setZeroPowerBehavior(BRAKE);
        frontRightMotor.setZeroPowerBehavior(BRAKE);
        backRightMotor.setZeroPowerBehavior(BRAKE);

        turretAccel.setZeroPowerBehavior(BRAKE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            //mecanum drivebase logic

            double y = gamepad1.left_stick_y; // left stick y axis
            double x = -gamepad1.left_stick_x * 1.1; // left stick x axis, multi to counteract imperfect strafing
            double rx = gamepad1.right_stick_x; // right stick x axis

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            // CHECK GM 0 FOR MATH, search mecanum teleop
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            frontLeftPower = ((y - x + rx) / denominator) /2;
            backLeftPower = ((y + x + rx) / denominator) /2;
            frontRightPower = ((y - x - rx) / denominator) /2;
            backRightPower = ((y + x - rx) / denominator) /2;

            // turret logic

            turretAccelPower = -gamepad1.right_trigger;

            frontLeftMotor.setPower(frontLeftPower);
            backLeftMotor.setPower(backLeftPower);
            frontRightMotor.setPower(frontRightPower);
            backRightMotor.setPower(backRightPower);

            turretAccel.setPower(turretAccelPower);


            // random comment just to test repo ownership transfer

        }
    }
}