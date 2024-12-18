package com.salaryc.salary.entity

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType
import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "attendance")
class Attendance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id:Long? = null,

    @Column(name = "check_in", nullable = false)
    var checkIn: LocalDateTime,

    @Column(name = "check_out")
    var checkOut: LocalDateTime? = null,

    @Column(name = "work_date", nullable = false)
    var workDate: LocalDate,

    @Type(PostgreSQLIntervalType::class)
    @Column(name = "duration")
    var duration: Duration? = null
    ) {
}