package com.radiantmood.kuttit.dev

import com.radiantmood.kuttit.data.server.KuttLink

fun KuttLink.Companion.preview() = KuttLink(
    id = "linkId",
    created_at = "2021-08-10T15:51:25.454Z",
    updated_at = "2021-08-10T15:51:25.454Z",
    address = "ysports",
    link = "http://radiantmood.com/ysports",
    target = "https://sports.yahoo.com/",
    description = null,
    password = false,
    visit_count = 9,
    banned = false,
    expire_in = null,
    domain = "radiantmood.com"
)