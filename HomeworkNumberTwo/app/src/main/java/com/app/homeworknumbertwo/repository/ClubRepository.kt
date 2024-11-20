package com.ranis.homeworknumbertwo

import com.app.homeworknumbertwo.repository.Club

object ClubRepository {
    var currentClubs: MutableList<Club> = mutableListOf()
    val clubs: MutableList<Club> = mutableListOf(
        Club(
            id = 0,
            name = "Real Madrid",
            url = "https://avto-nakleyki66.ru/uploads/product/1981/loupe.png",
            info = "«Реал Мадрид» — испанский профессиональный футбольный клуб из города Мадрид.\n" +
                    "Основан 6 марта 1902 года.\n" +
                    "Признан ФИФА лучшим футбольным клубом XX века.",
        ),
        Club(
            id = 1,
            name = "Barcelona",
            url = "https://1.allegroimg.com/original/0153fe/2a13807144f7af9915af1621d261/Naklejki-pilkarskie-FC-BARCELONA-na-sciane-100cm",
            info = "«Барселона» — испанский профессиональный футбольный клуб из одноимённого города.\n" +
                    "Основан в 1899 году группой швейцарских, британских, испанских и каталонских футболистов во главе с Жоаном Гампером.",
        ),
        Club(
            id = 2,
            name = "Manchester City",
            url = "https://sun9-60.userapi.com/impg/QRJ6wjM3NiQDx6Q-7PeMkrg6-08k1iMG4iqh8g/6pcYfuo4Ch4.jpg?size=604x604&quality=96&sign=4c90d7532ac90d1de15df924ff6ac0f5&type=album",
            info = "Футбольный клуб \"Манчестер Сити\" - профессиональный футбольный клуб, базирующийся в" +
                    " Манчестере, Англия. Клуб выступает в Премьер-лиге, высшем эшелоне английского футбола. ",
            ),
        Club(
            id = 3,
            name = "Bayern Munich",
            url = "https://img.staticdj.com/3258aa6b7b8b5d1a3676b60092e898c3.jpeg",
            info = "«Бавария» — немецкий футбольный клуб из города Мюнхен. Основан в 1900 году.\n" +
                    "\n" +
                    "Самый титулованный клуб Германии и один из самых титулованных клубов мира. На его счету " +
                    "33 чемпионских титула и 20 Кубков Германии",
        ),
        Club(
            id = 4,
            name = "Chelsea",
            url = "https://yt3.googleusercontent.com/GGZ9lF6Dj-EI9HCss0I_G1AXGrhzIoGtoA1iR3qSRe2toKEsOY7wq_3meKGJUNcxbSwn2Ig3=s900-c-k-c0x00ffffff-no-rj",
            info = "Футбольный клуб \"Челси\" - профессиональный футбольный клуб, базирующийся в Фулхэме, " +
                    "Западный Лондон, Англия. Клуб выступает в Премьер-лиге, высшем эшелоне английского футбола. ",

            ),
        Club(
            id = 5,
            name = "PSG",
            url = "https://www.pngmart.com/files/23/Psg-Logo-PNG-Photo.png",
            info = "Футбольный клуб \"Пари Сен-Жермен\" обычно называемый Пари Сен-Жермен или просто ПСЖ, " +
                    "является профессиональным футбольным клубом, базирующимся в Париже, Франция. ",

            ),
        Club(
            id = 6,
            name = "Borussia",
            url = "https://mychamp.ru/system/clubs/11024/large.png",
            info = "«Боруссия» — профессиональный немецкий футбольный клуб из города Дортмунд, земля Северный Рейн-Вестфалия.\n" +
                    "Основан в 1909 году.",
        ),
        Club(
            id = 7,
            name = "Atletico Madrid",
            url = "https://upload.wikimedia.org/wikipedia/be-x-old/thumb/3/32/Atletico_Madrid.svg/1200px-Atletico_Madrid.svg.png",
            info = "«Атле́тико Мадри́д» (исп. Club Atlético de Madrid, S.A.D.) — испанский профессиональный футбольный клуб из Мадрида, в одноимённом автономном сообществе. " +
                    "Клуб основан 26 апреля 1903 года"
        ),
        Club(
            id = 8,
            name = "Arsenal",
            url = "https://sport.guim.co.uk/football/crests/1006.png",
            info = "«Арсена́л» — английский профессиональный футбольный клуб из Северного Лондона (боро Ислингтон), " +
                    "выступающий в Премьер-лиге.",
        ),
        Club(
            id = 9,
            name = "Liverpool",
            url = "https://p16-sign-useast2a.tiktokcdn.com/tos-useast2a-avt-0068-euttp/5839883dc71e3fa1dd2112e8f51375ff~c5_1080x1080.jpeg?lk3s=a5d48078&nonce=73115&refresh_token=38795902388ecc895a1fb015afab7134&x-expires=1731873600&x-signature=6bQtrQor%2BFlmo96LuqyFC%2BJcHZY%3D&shp=a5d48078&shcp=81f88b70",
            info = "«Ливерпу́ль» — английский профессиональный футбольный клуб из одноимённого города, " +
                    "расположенного в графстве Мерсисайд.",
        ),
        Club(
            id = 10,
            name = "Bayer",
            url = "https://avatars.mds.yandex.net/i?id=aea0f322c81e3ee9a834ec7a59f02a3bc5e35cf0-4490973-images-thumbs&n=13",
            info = "«Ба́йер 04» — немецкий профессиональный футбольный клуб из города Леверкузен, Северный Рейн-Вестфалия. Основан 1 июля 1904 году.",
        ),
        Club(
            id = 11,
            name = "Napoli",
            url = "https://www.roma.sportland24.ru/service/images/team_photo/Napoli.png",
            info = "«На́поли» — итальянский профессиональный футбольный клуб из Неаполя, основан в 1926 году. В настоящее время играет в Серии А.",
        ),
        Club(
            id = 12,
            name = "Inter",
            url = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/FC_Internazionale_Milano_2014.svg/1200px-FC_Internazionale_Milano_2014.svg.png",
            info = "«Интернациона́ле», или просто «Интер» — итальянский профессиональный футбольный клуб из города Милан, выступающий в Серии А. ",
        ),
    )
}