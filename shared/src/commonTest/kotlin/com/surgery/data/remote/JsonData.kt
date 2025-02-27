package com.surgery.data.remote

import com.surgery.data.model.ProcedureDetailsDto
import com.surgery.data.model.ProcedureDto
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

internal const val proceduresJson = """
    [
  {
    "uuid": "procedure-TSC_CemCup",
    "icon": {
      "uuid": "ICON-159133",
      "url": "https://content-staging.touchsurgery.com/5d/",
      "version": 100
    },
    "name": "Cemented Hip Cup",
    "phases": [
      "module-TSC_CemCup_001"
    ],
    "specialties": [
      "3a25c514-4d63-4150-b75e-9131466f82d8",
      "c37cc6d6-725c-496e-a1b5-7fed2c871eab"
    ],
    "deep_link": "https://lh68.app.link/",
    "author": "",
    "organisation": "Touch Surgery",
    "doi_code": "",
    "date_published": "2015-04-14T10:00:51.940",
    "labels": [],
    "site_slug": "cemented-hip-cup",
    "duration": 2428,
    "is_purchasable": false
  },
  {
    "uuid": "procedure-TSC_HipHemi",
    "icon": {
      "uuid": "ICON-13270",
      "url": "https://content-staging.touchsurgery.com/11/4c",
      "version": 100
    },
    "name": "Cemented Hip Hemiarthroplasty",
    "phases": [
      "module-TSC_HipHemi_001"
    ],
    "specialties": [
      "3a25c514-4d63-4150-b75e-9131466f82d8",
      "c37cc6d6-725c-496e-a1b5-7fed2c871eab"
    ],
    "deep_link": "https://lh68.app.link/",
    "author": "",
    "organisation": "Touch Surgery",
    "doi_code": "",
    "date_published": "2015-04-14T10:00:51.949",
    "labels": [],
    "site_slug": "cemented-hip-hemiarthroplasty",
    "duration": 3402,
    "is_purchasable": false
  }
]
"""

internal val procedureDtoList = json.decodeFromString<List<ProcedureDto>>(proceduresJson)

internal const val procedureDetailsDtoJson = """
    {
  "uuid": "procedure-TSC_CemCup",
  "name": "Cemented Hip Cup",
  "phases": [
    {
      "uuid": "module-TSC_CemCup_001",
      "name": "Cemented Hip Cup",
      "icon": {
        "uuid": "ICON-159132",
        "url": "https://content-staging.touchsurgery.com/5d/21/5d2178b3c0c2ed2d61197bc3f7f8c3940747af70e99ab51c2058fb55fe12698c?Expires=1741092641&Signature=ITTXBcxuiuFD0p5fnQLg~OzpUv6SVH555Z7QrBbOF0mHJSVOlufs2s0L2FqQG9U4tBW9kpLHlcdOrR~iN~89~ciqLA5~LNINSGFEBxEQm76rSHfM-LMs4SwipH27WZFk0s5LVJHhL3gZMGxS9eFy~f5RNTx~45RUSSyC1TyhhefJUpazB5IEU0Iri0VnggbUjjkMLlRRrLpPiAWg49sBgZ4b4o~5UI7gYWRszhMiDWyYxcAss8cJ-jXoMUv4VwxHS8FZ7kzF0DI~lp~3KkUQwIA-AQ6L3IWKdMf0XfSy2HOiP51LglCBK-OcRs4tFlKPg~tvkSWTJLV4fcq3i0TuwQ__&Key-Pair-Id=KNNS9X5VSGQAG",
        "version": 100
      },
      "deep_link": "",
      "test_mode": true,
      "max_user_score": null,
      "viewed": false,
      "learn_completed": false
    }
  ],
  "icon": {
    "uuid": "ICON-159133",
    "url": "https://content-staging.touchsurgery.com/5d/21/5d2178b3c0c2ed2d61197bc3f7f8c3940747af70e99ab51c2058fb55fe12698c?Expires=1741092641&Signature=ITTXBcxuiuFD0p5fnQLg~OzpUv6SVH555Z7QrBbOF0mHJSVOlufs2s0L2FqQG9U4tBW9kpLHlcdOrR~iN~89~ciqLA5~LNINSGFEBxEQm76rSHfM-LMs4SwipH27WZFk0s5LVJHhL3gZMGxS9eFy~f5RNTx~45RUSSyC1TyhhefJUpazB5IEU0Iri0VnggbUjjkMLlRRrLpPiAWg49sBgZ4b4o~5UI7gYWRszhMiDWyYxcAss8cJ-jXoMUv4VwxHS8FZ7kzF0DI~lp~3KkUQwIA-AQ6L3IWKdMf0XfSy2HOiP51LglCBK-OcRs4tFlKPg~tvkSWTJLV4fcq3i0TuwQ__&Key-Pair-Id=KNNS9X5VSGQAG",
    "version": 100
  },
  "card": {
    "uuid": "CARD-159134",
    "url": "https://content-staging.touchsurgery.com/a3/1a/a31aaf26d8395b3a81d0414228dfa7f413286fc34bf7b69fc12ee82c9250ad3d?Expires=1741092641&Signature=CFjrbvAuhRVLZDR4XY6433BDNGqxoGyv0jfCYMN20ok8-ROZrof9jNScLNHOz9lYHFanlVdvincaI1H1gcg2jHPpiq46K9I~FH8WfolWLN7HzzcySHMiWVmMA5kScKP-xufQpf5zXSPEYfmegXNXfndd-wjlsCnwMOKtCWfV-c9BOed0aU1f3TVHXmLnbmHiNXVjClwPXQ52KDUerVTcaiSTu3~-SEdRNkaKoHxqoL2zSm9bBfq5HeD5vJul5zgVfykL16XpHd1PzEuYq0QeNh4z7XhVnvcWGu6F3BSAbZFzYNmPoV97c2JGdOkTcNH8m3sMgf2fC2Cj18Ou~pZNjw__&Key-Pair-Id=KNNS9X5VSGQAG",
    "version": 100
  },
  "specialties": [
    "c37cc6d6-725c-496e-a1b5-7fed2c871eab",
    "3a25c514-4d63-4150-b75e-9131466f82d8"
  ],
  "labels": [
    
  ],
  "channel": {
    "banner": {
      "uuid": "BANNER-233997",
      "url": "https://content-staging.touchsurgery.com/c8/d9/",
      "version": 100
    }
  },
  "overview": [
    1523,
    1524
  ],
  "devices": [
    
  ],
  "deep_link": "https://lh68.app.link/",
  "view_count": 0,
  "author": "",
  "organisation": "Touch Surgery",
  "doi_code": "",
  "date_published": "2015-04-14T10:00:51.940581",
  "duration": 2428,
  "product_ids": {
    
  },
  "is_purchasable": false,
  "product_info_url": null,
  "cpd_credits": null
}
"""

internal val procedureDetailsDto =
    json.decodeFromString<ProcedureDetailsDto>(procedureDetailsDtoJson)