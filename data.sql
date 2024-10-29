insert into authority (id, name) values (1, 'Полиция');
insert into authority (id, name) values (2, 'Таможенная cлужба');
insert into authority (id, name) values (3, 'Пограничная служба');
insert into authority (id, name) values (4, 'Агентство по защите природы');

insert into discovery_method(id, method) VALUES (1, 'Сканирование');
insert into discovery_method(id, method) VALUES (2, 'Оценка рисков');
insert into discovery_method(id, method) VALUES (3, 'Выборочная проверка');
insert into discovery_method(id, method) VALUES (4, 'Собака-ищейка');
insert into discovery_method(id, method) VALUES (5, 'Информация третьих лиц');
insert into discovery_method(id, method) VALUES (6, 'Физический осмотр');

insert into reason_for_seizure(id, reason) values (1, 'Отсутствие разрешения СИТЕС');
insert into reason_for_seizure(id, reason) values (2, 'Незадекларированный');
insert into reason_for_seizure(id, reason) values (3, 'Незаконное пересечение');
insert into reason_for_seizure(id, reason) values (4, 'Браконьерство');
insert into reason_for_seizure(id, reason) values (5, 'Неизвестный');

insert into transport_method (id, method) VALUES (1, 'Железнодорожный');
insert into transport_method (id, method) VALUES (2, 'Морской');
insert into transport_method (id, method) VALUES (3, 'Автодорожный');
insert into transport_method (id, method) VALUES (4, 'Воздушный');
insert into transport_method (id, method) VALUES (5, 'Почтовый');

insert into trade_direction(id, direction) VALUES (1, 'Внутренний');
insert into trade_direction(id, direction) VALUES (2, 'Экспорт');
insert into trade_direction(id, direction) VALUES (3, 'Импорт');


INSERT INTO country (id, name, short_name, code)
VALUES ('e8377e6d-c8d6-4c06-8b56-110f702a6f2c', 'Соединенные Штаты Америки', 'USA', '840');
INSERT INTO country (id, name, short_name, code)
VALUES ('a94874a3-fb88-4c96-a07a-8f0f7b5e5ee3', 'Соединенное Королевство', 'GBR', '826');
INSERT INTO country (id, name, short_name, code)
VALUES ('d9eab75f-2f59-48c2-82e7-fb02a2cfd4a6', 'Франция', 'FRA', '250');
INSERT INTO country (id, name, short_name, code)
VALUES ('f1aebca4-c88d-4640-a439-d4f3d9f6f62b', 'Германия', 'GER', '036');
INSERT INTO country (id, name, short_name, code)
VALUES ('4cb95e5c-d097-48c8-8e48-15292eb387a9', 'Япония', 'JPN', '392');
INSERT INTO country (id, name, short_name, code)
VALUES ('dbf1e453-7d75-4c4c-b0bf-5c688968a12e', 'Китай', 'CHN', '156');
INSERT INTO country (id, name, short_name, code)
VALUES ('2dfe8a45-e40a-4e0b-bbc8-7eb0a6ba5e27', 'Кыргызстан', 'KGZ', '417');
INSERT INTO country (id, name, short_name, code)
VALUES ('b0985081-c745-4c52-b02c-8063f55fc677', 'Россия', 'RUS', '643');
INSERT INTO country (id, name, short_name, code)
VALUES ('b5e7c464-78b5-4d27-9daa-7ef185bfec59', 'Австралия', 'AUS', '036');
INSERT INTO country (id, name, short_name, code)
VALUES ('0d8d6c16-1bf5-4ea9-8ff4-fc3df3b20ba4', 'Канада', 'CAN', '124');
INSERT INTO country (id, name, short_name, code)
VALUES ('cba8b5b6-68d7-4f9e-9a1d-b3cce0dc537e', 'Австрийская Республика', 'AUT', '040');
INSERT INTO country (id, name, short_name, code)
VALUES ('1fa5282b-04d4-4b1f-9b9e-63d38c88fca1', 'Алжирская Народная Демократическая Республика', 'DZA', '012');
INSERT INTO country (id, name, short_name, code)
VALUES ('09e7a9b2-6e23-4b28-bcc0-5e507dc6c7ed', 'Американское Самоа', 'ASM', '016');
INSERT INTO country (id, name, short_name, code)
VALUES ('04dc7590-d4c7-43e7-94e6-e57d3f9cda9e', 'Ангилья', 'AIA', '660');
INSERT INTO country (id, name, short_name, code)
VALUES ('4b048d52-4d20-48e8-9872-15758b85d2c5', 'Антарктида', 'ATA', '010');
INSERT INTO country (id, name, short_name, code)
VALUES ('9b7a59a4-1837-4f93-b8c4-6b5c0867819b', 'Антигуа и Барбуда', 'ATG', '028');
INSERT INTO country (id, name, short_name, code)
VALUES ('e8e5d92f-89e4-4f8e-ae7e-9a098d9eec69', 'Арабская Республика Египет', 'EGY', '818');
INSERT INTO country (id, name, short_name, code)
VALUES ('8edc42d8-67b0-4525-9dd0-45ed36aee9a6', 'Аргентинская Республика', 'ARG', '032');
INSERT INTO country (id, name, short_name, code)
VALUES ('abc8f72d-d9c8-45b5-8198-d8428e1c18c2', 'Аруба', 'ABW', '533');
INSERT INTO country (id, name, short_name, code)
VALUES ('5b4c56e2-0f90-481c-b243-75c1e0c028cb', 'Барбадос', 'BRB', '052');
INSERT INTO country (id, name, short_name, code)
VALUES ('90b3e508-e308-4b57-9379-e2b08eaed5d6', 'Белиз', 'BLZ', '084');
INSERT INTO country (id, name, short_name, code)
VALUES ('aed6c889-c8d2-4019-8c9d-39ae7bb3dcd2', 'Бермуды', 'BMU', '060');
INSERT INTO country (id, name, short_name, code)
VALUES ('b05a1de0-3b26-4970-9c26-6a899c6fa94d', 'Бонэйр, Синт-Эстатиус и Саба', 'BES', '535');
INSERT INTO country (id, name, short_name, code)
VALUES ('21c1b56a-b1eb-42b2-86b4-60fc95ac9335', 'Босния и Герцеговина', 'BIH', '070');
INSERT INTO country (id, name, short_name, code)
VALUES ('214ec5c4-43e6-4598-b0e8-437b55a8b5e7', 'Британская территория в Индийском океане', 'IOT', '086');
INSERT INTO country (id, name, short_name, code)
VALUES ('d55d1744-e72d-4f46-a6a5-4c6709dd1ae1', 'Бруней-Даруссалам', 'BRN', '096');
INSERT INTO country (id, name, short_name, code)
VALUES ('918c4580-60ea-4f60-a580-b5b63e68f2e2', 'Буркина-Фасо', 'BFA', '854');
INSERT INTO country (id, name, short_name, code)
VALUES ('3581f2e0-48a2-45b4-a604-845c8b8be244', 'Великое Герцогство Люксембург', 'LUX', '442');
INSERT INTO country (id, name, short_name, code)
VALUES ('8d6de4df-77b1-48eb-a8e2-7b1c4dc7870b', 'Венгрия', 'HUN', '348');
INSERT INTO country (id, name, short_name, code)
VALUES ('c66cbf61-b8a5-4fbf-844e-3b6f5f2da1d4', 'Венесуэла (Боливарианская Республика)', 'VEN', '862');
INSERT INTO country (id, name, short_name, code)
VALUES ('112c676d-8397-478e-8e1b-2842bff65c59', 'Виргинские острова (Британские)', 'VGB', '092');
INSERT INTO country (id, name, short_name, code)
VALUES ('b5e12d21-1d2b-4b64-8007-d3e60a9916ae', 'Виргинские острова (США)', 'VIR', '850');
INSERT INTO country (id, name, short_name, code)
VALUES ('2a198e7d-2f75-4c36-9ad4-1c2c7e65dd7c', 'Восточная Республика Уругвай', 'URY', '858');
INSERT INTO country (id, name, short_name, code)
VALUES ('9d5c0af6-230d-487f-953d-d71ed7bfa2c1', 'Габонская Республика', 'GAB', '266');
INSERT INTO country (id, name, short_name, code)
VALUES ('a7369a61-b87e-4931-840d-63c87a1d45e1', 'Гваделупа', 'GLP', '312');
INSERT INTO country (id, name, short_name, code)
VALUES ('be9fc73b-6747-4983-8b5b-5da91c30d634', 'Гвинейская Республика', 'GIN', '324');
INSERT INTO country (id, name, short_name, code)
VALUES ('ce40c6e8-8509-4bdb-90f7-8151295e9d5c', 'Гернси', 'GGY', '831');
INSERT INTO country (id, name, short_name, code)
VALUES ('0f478109-e53a-48f4-a8ee-e23be9475f3b', 'Гибралтар', 'GIB', '292');
INSERT INTO country (id, name, short_name, code)
VALUES ('d2cfa5ee-b112-4260-b0b0-9e4d78de9cb2', 'Государство Израиль', 'ISR', '376');
INSERT INTO country (id, name, short_name, code)
VALUES ('a6cf56e5-5ae4-4c0a-8ffb-0af3b77a2938', 'Государство Катар', 'QAT', '634');
INSERT INTO country (id, name, short_name, code)
VALUES ('4a7a7a59-4263-41e4-a5e3-2f8f2d00c060', 'Государство Кувейт', 'KWT', '414');
INSERT INTO country (id, name, short_name, code)
VALUES ('f5e1d190-2e3b-4bfb-8a27-05ea1f438db1', 'Государство Ливия', 'LBY', '434');
INSERT INTO country (id, name, short_name, code)
VALUES ('62b4f844-5b0f-48f0-87a6-b22dcefbf4f0', 'Государство Палестина', 'PSE', '275');
INSERT INTO country (id, name, short_name, code)
VALUES ('42dfc2a1-96bb-4ccf-b231-36d8d6826593', 'Государство Эритрея', 'ERI', '232');
INSERT INTO country (id, name, short_name, code)
VALUES ('fbd95d0e-d5c3-4efb-bdc1-68879fbc51e3', 'Гренада', 'GRD', '308');
INSERT INTO country (id, name, short_name, code)
VALUES ('530a3f68-bc9a-459d-a89b-34ec6c2a4f62', 'Гренландия', 'GRL', '304');
INSERT INTO country (id, name, short_name, code)
VALUES ('86ef8f15-bded-4b3d-8b97-650e702a57fc', 'Греческая Республика', 'GRC', '300');
INSERT INTO country (id, name, short_name, code)
VALUES ('6da4b308-e1e1-4cd8-957b-eef34b94d682', 'Грузия', 'GEO', '268');
INSERT INTO country (id, name, short_name, code)
VALUES ('5d56d2d5-f6d0-4ac8-97fa-1805b4e01a6b', 'Гуам', 'GUM', '316');
INSERT INTO country (id, name, short_name, code)
VALUES ('02dce621-c688-46d6-a348-d7f2be5e1f82', 'Демократическая Республика Конго', 'COD', '180');
INSERT INTO country (id, name, short_name, code)
VALUES ('a3a9611d-0272-43c1-bf0f-6f59b5a6b597', 'Демократическая Республика Сан-Томе и Принсипи', 'STP', '678');
INSERT INTO country (id, name, short_name, code)
VALUES ('8092b6e4-b748-40c0-b841-4705487cc6ed', 'Демократическая Республика Тимор-Лесте', 'TLS', '626');
INSERT INTO country (id, name, short_name, code)
VALUES ('2e0e0d9c-2e67-4569-b42e-bb5a7c807fd7', 'Демократическая Социалистическая Республика Шри-Ланка', 'LKA', '144');
INSERT INTO country (id, name, short_name, code)
VALUES ('4da5c615-57b6-4688-9447-93f9930ea91b', 'Джерси', 'JEY', '832');
INSERT INTO country (id, name, short_name, code)
VALUES ('b0ecf2e0-3e2b-4f58-99ff-02a4d0bbf78f', 'Для лиц без гражданства', null, '999');
INSERT INTO country (id, name, short_name, code)
VALUES ('e07b9d43-8de7-40da-9e45-3b511004ddfd', 'Доминиканская Республика', 'DOM', '214');
INSERT INTO country (id, name, short_name, code)
VALUES ('7d0c4f39-bb8a-4590-99b1-5e0e917c55c1', 'Донецкая Народная Республика', 'DNR', '897');
INSERT INTO country (id, name, short_name, code)
VALUES ('b31c1d35-f7c2-4d4e-a6ea-dbe924d74c3f', 'Западная Сахара', 'ESH', '732');
INSERT INTO country (id, name, short_name, code)
VALUES ('21f8c929-7909-4b7e-8b2f-9c18d94fa4e1', 'Иорданское Хашимитское Королевство', 'JOR', '400');
INSERT INTO country (id, name, short_name, code)
VALUES ('dc7b9460-bb9d-4518-a9ed-d9698d3e63da', 'Иран (Исламская Республика)', 'IRN', '364');
INSERT INTO country (id, name, short_name, code)
VALUES ('5a234c6d-cd85-42ae-9129-9a6a2dfcbf5e', 'Ирландия', 'IRL', '372');
INSERT INTO country (id, name, short_name, code)
VALUES ('ec7e1f2c-9c3d-4f73-80cb-3b453b11df62', 'Исламская Республика Афганистан', 'AFG', '004');
INSERT INTO country (id, name, short_name, code)
VALUES ('2d1f6b84-9e60-4de4-a764-e1ffea82c579', 'Исламская Республика Гамбия', 'GMB', '270');
INSERT INTO country (id, name, short_name, code)
VALUES ('a5e7f7c3-3d62-4269-8c7e-49d6e5e4905a', 'Исламская Республика Мавритания', 'MRT', '478');
INSERT INTO country (id, name, short_name, code)
VALUES ('927db2d8-0b5c-4877-8c8e-87b4a5d89a3b', 'Исламская Республика Пакистан', 'PAK', '586');
INSERT INTO country (id, name, short_name, code)
VALUES ('c6f8f91c-6f48-4b6d-a7ea-1e0c4809c8fa', 'Итальянская Республика', 'ITA', '380');
INSERT INTO country (id, name, short_name, code)
VALUES ('b3c06f20-8a56-4fa4-bf0b-cb7451c0e2d4', 'Йеменская Республика', 'YEM', '887');
INSERT INTO country (id, name, short_name, code)
VALUES ('c8b9b1ef-d0cf-4b2d-bc72-e8e4d85b2349', 'Княжество Андорра', 'AND', '020');
INSERT INTO country (id, name, short_name, code)
VALUES ('efdb09bc-dab3-43c7-bff0-09472e3c7ed6', 'Княжество Лихтенштейн', 'LIE', '438');
INSERT INTO country (id, name, short_name, code)
VALUES ('b3d4d9b2-bcd1-42c6-8e4b-2924c1c4d2e4', 'Княжество Монако', 'MCO', '492');
INSERT INTO country (id, name, short_name, code)
VALUES ('8dc906fc-92f1-43b6-b2f7-95b0ea8c7463', 'Кокосовые (Килинг) острова', 'CCK', '166');
INSERT INTO country (id, name, short_name, code)
VALUES ('46e3b98b-31e3-47b3-b0b0-21eaf1a489c1', 'Кооперативная Республика Гайана', 'GUY', '328');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440021', 'Республика Кот д`Ивуар', 'CIV', '384');
INSERT INTO country (id, name, short_name, code)
VALUES ('7ec84a02-7342-45a6-bf38-8814d8b7699b', 'Корейская Народно-Демократическая Республика', 'PRK', '408');
INSERT INTO country (id, name, short_name, code)
VALUES ('db5f59e1-7a91-46b4-9f43-f5de0f2bb9d5', 'Королевство Бахрейн', 'BHR', '048');
INSERT INTO country (id, name, short_name, code)
VALUES ('c1f1c6b1-3a84-4d7f-ae65-50dc2e0f728f', 'Королевство Бельгии', 'BEL', '056');
INSERT INTO country (id, name, short_name, code)
VALUES ('7f6d292e-1b3c-4b29-95d5-15e8d4d4c1c8', 'Королевство Бутан', 'BTN', '064');
INSERT INTO country (id, name, short_name, code)
VALUES ('df2e2c45-7459-4872-a391-9f3ccf54e438', 'Королевство Дания', 'DNK', '208');
INSERT INTO country (id, name, short_name, code)
VALUES ('f58b072d-92a0-4a26-ae9e-1d6db7fd759e', 'Королевство Испания', 'ESP', '724');
INSERT INTO country (id, name, short_name, code)
VALUES ('b9a9e47d-0d71-43d6-bd7c-69cf7d41dcfd', 'Королевство Камбоджа', 'KHM', '116');
INSERT INTO country (id, name, short_name, code)
VALUES ('c07c5872-e70b-4a4e-8485-5e31b8f4db58', 'Королевство Лесото', 'LSO', '426');
INSERT INTO country (id, name, short_name, code)
VALUES ('8dabe6e4-f349-4c6f-a2b7-1740fc99ec27', 'Королевство Марокко', 'MAR', '504');
INSERT INTO country (id, name, short_name, code)
VALUES ('c55dded6-93b2-4a1d-a5da-e5b746e84b2b', 'Королевство Нидерландов', 'NLD', '528');
INSERT INTO country (id, name, short_name, code)
VALUES ('90b65d59-0b2b-49f3-87c2-855f1b73d41a', 'Королевство Норвегия', 'NOR', '578');
INSERT INTO country (id, name, short_name, code)
VALUES ('ebc9f81c-62c1-4f84-a3f4-ef0e1d23e8f0', 'Королевство Саудовская Аравия', 'SAU', '682');
INSERT INTO country (id, name, short_name, code)
VALUES ('9d5f7bce-21a3-4b7d-917d-3e90baf6a066', 'Королевство Таиланд', 'THA', '764');
INSERT INTO country (id, name, short_name, code)
VALUES ('23f67671-dbd3-4d58-b5b4-2231a6c3b447', 'Королевство Тонга', 'TON', '776');
INSERT INTO country (id, name, short_name, code)
VALUES ('afc0e9ed-6824-4fc8-b2de-1d9eaa7f3f87', 'Королевство Швеция', 'SWE', '752');
INSERT INTO country (id, name, short_name, code)
VALUES ('dc891c3e-4d1e-445e-a5d0-11a0d4b7ea92', 'Королевство Эсватини', 'SWZ', '748');
INSERT INTO country (id, name, short_name, code)
VALUES ('68c2e79b-d22e-4e3d-b47f-91809e4e2d78', 'Кюрасао', 'CUW', '531');
INSERT INTO country (id, name, short_name, code)
VALUES ('b9a7c873-22b3-4856-8d87-4f643c1b4f66', 'Лаосская Народно-демократическая Республика', 'LAO', '418');
INSERT INTO country (id, name, short_name, code)
VALUES ('e578b8e7-7f51-4b9b-872d-cf94c88c3a57', 'Латвийская Республика', 'LVA', '428');
INSERT INTO country (id, name, short_name, code)
VALUES ('9a5a295b-c61d-4e7e-9aef-7efbb0e4a388', 'Ливанская Республика', 'LBN', '422');
INSERT INTO country (id, name, short_name, code)
VALUES ('9b7f29c6-8d45-4e86-b6b3-fd2b0d6ed0cb', 'Литовская Республика', 'LTU', '440');
INSERT INTO country (id, name, short_name, code)
VALUES ('64bbf062-5447-49d1-8c72-882b4d4a754f', 'Луганская Народная Республика', 'LNR', '898');
INSERT INTO country (id, name, short_name, code)
VALUES ('b7b9e6bb-0351-4a90-aeb7-0d7a1344c27f', 'Майотта', 'MYT', '175');
INSERT INTO country (id, name, short_name, code)
VALUES ('52db98d7-1b9b-4e99-8e8d-70bb6f4ec847', 'Малайзия', 'MYS', '458');
INSERT INTO country (id, name, short_name, code)
VALUES ('a6d4d40b-0f90-499d-b29e-d2a2eeb9794c', 'Малые Тихоокеанские Отдаленные острова Соединенных Штатов', 'UMI',
        '581');
INSERT INTO country (id, name, short_name, code)
VALUES ('df5de4cb-9a9e-49b2-a81f-e90b2d3a4498', 'Мальдивская Республика', 'MDV', '462');
INSERT INTO country (id, name, short_name, code)
VALUES ('e4a4737e-e4b5-43d0-86b0-bc67c0a6f3a5', 'Мартиника', 'MTQ', '474');
INSERT INTO country (id, name, short_name, code)
VALUES ('7b06d0d3-7dc5-40f2-81cc-d8d2e4cfd5db', 'Мексиканские Соединенные Штаты', 'MEX', '484');
INSERT INTO country (id, name, short_name, code)
VALUES ('b4b0f4d4-0894-4640-a6e7-4d70e16f35ea', 'Многонациональное Государство Боливия', 'BOL', '068');
INSERT INTO country (id, name, short_name, code)
VALUES ('d2732e63-16b2-4c1a-bf92-038fe6b9cf54', 'Монголия', 'MNG', '496');
INSERT INTO country (id, name, short_name, code)
VALUES ('9cfd437f-f8a1-458a-bb32-45926b0bfe2d', 'Монтсеррат', 'MSR', '500');
INSERT INTO country (id, name, short_name, code)
VALUES ('3bb3f38f-8ae7-4527-a00e-8a0a4aab38d6', 'Народная Республика Бангладеш', 'BGD', '050');
INSERT INTO country (id, name, short_name, code)
VALUES ('16d8f1e1-5a22-4b6b-9cf6-737f6e6a7fc8', 'Независимое Государство Папуа Новая Гвинея', 'PNG', '598');
INSERT INTO country (id, name, short_name, code)
VALUES ('d2dbf075-8056-4f9b-b0a4-22f1c577b8cb', 'Независимое Государство Самоа', 'WSM', '882');
INSERT INTO country (id, name, short_name, code)
VALUES ('b2c4f57a-2b36-4b78-a4de-9a7cf379e6f5', 'Непал', 'NPL', '524');
INSERT INTO country (id, name, short_name, code)
VALUES ('a7c9d26b-36c4-4d91-810d-6c7a6d9208c3', 'Ниуэ', 'NIU', '570');
INSERT INTO country (id, name, short_name, code)
VALUES ('db546b08-28d5-4c35-8619-091c6c9a2d42', 'Новая Зеландия', 'NZL', '554');
INSERT INTO country (id, name, short_name, code)
VALUES ('5f3c6b51-8f1a-4d53-9cce-b1d4ae0fc7e2', 'Новая Каледония', 'NCL', '540');
INSERT INTO country (id, name, short_name, code)
VALUES ('73d2c6a1-fba0-4d80-8f09-354b5ef3d041', 'Нормандские острова', null, '830');
INSERT INTO country (id, name, short_name, code)
VALUES ('3b8b5d54-1604-4e22-91c2-d63ea7727069', 'Объединенная Республика Танзания', 'TZA', '834');
INSERT INTO country (id, name, short_name, code)
VALUES ('f587d0d0-6508-438e-b7ed-f9b3c5cc1f79', 'Объединенные Арабские Эмираты', 'ARE', '784');
INSERT INTO country (id, name, short_name, code)
VALUES ('c6a647f1-5d71-4c92-8237-fd7e2d5a1b09', 'Остров Буве', 'BVT', '074');
INSERT INTO country (id, name, short_name, code)
VALUES ('ecdcda3a-e38a-4d6f-8c46-85b7458d798b', 'Остров Мэн', 'IMN', '833');
INSERT INTO country (id, name, short_name, code)
VALUES ('6df4b0c6-0297-4cc7-a283-1dff33989e1a', 'Остров Норфолк', 'NFK', '574');
INSERT INTO country (id, name, short_name, code)
VALUES ('e648b50d-2756-4142-b7b4-d6a42d1f5653', 'Остров Рождества', 'CXR', '162');
INSERT INTO country (id, name, short_name, code)
VALUES ('55f72f85-7000-47e3-ae89-8a3f44b7a7a0', 'Остров Херд и острова Макдональд', 'HMD', '334');
INSERT INTO country (id, name, short_name, code)
VALUES ('f0436b48-c8b3-4b1f-8b0a-0dc8a8b09c47', 'Острова Кайман', 'CYM', '136');
INSERT INTO country (id, name, short_name, code)
VALUES ('a1fa8d46-90a6-4f26-bc32-cd6b79f21d72', 'Острова Кука', 'COK', '184');
INSERT INTO country (id, name, short_name, code)
VALUES ('4a3e65ae-2213-4e69-a3d4-8f7a72e7927b', 'Острова Теркс и Кайкос', 'TCA', '796');
INSERT INTO country (id, name, short_name, code)
VALUES ('3e042c07-c4c5-40ec-8b9c-7f6e045e6e6c', 'Папский престол (Государство-город Ватикан)', 'VAT', '336');
INSERT INTO country (id, name, short_name, code)
VALUES ('7d44f891-6067-4d63-bb69-6a7acb8317a2', 'Питкерн', 'PCN', '612');
INSERT INTO country (id, name, short_name, code)
VALUES ('d8d4c5da-94d5-4429-b92d-e94c691a5c4e', 'Португальская Республика', 'PRT', '620');
INSERT INTO country (id, name, short_name, code)
VALUES ('ec87b54d-c7b2-40e1-9f24-8c22042385f8', 'Пуэрто-Рико', 'PRI', '630');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440000', 'Республика Абхазия', 'ABH', '895');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 'Республика Азербайджан', 'AZE', '031');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440002', 'Республика Албания', 'ALB', '008');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440003', 'Республика Ангола', 'AGO', '024');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440004', 'Республика Армения', 'ARM', '051');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440005', 'Республика Беларусь', 'BLR', '112');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440006', 'Республика Бенин', 'BEN', '204');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440007', 'Республика Болгария', 'BGR', '100');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440008', 'Республика Ботсвана', 'BWA', '072');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440009', 'Республика Бурунди', 'BDI', '108');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544000a', 'Республика Вануату', 'VUT', '548');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544000b', 'Республика Гаити', 'HTI', '332');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544000c', 'Республика Гана', 'GHA', '288');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544000d', 'Республика Гватемала', 'GTM', '320');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544000e', 'Республика Гвинея-Бисау', 'GNB', '624');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544000f', 'Республика Гондурас', 'HND', '340');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440010', 'Республика Джибути', 'DJI', '262');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440011', 'Республика Замбия', 'ZMB', '894');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440012', 'Республика Зимбабве', 'ZWE', '716');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440013', 'Республика Индия', 'IND', '356');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440014', 'Республика Индонезия', 'IDN', '360');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440015', 'Республика Ирак', 'IRQ', '368');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440016', 'Республика Исландия', 'ISL', '352');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440017', 'Республика Кабо-Верде', 'CPV', '132');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440018', 'Республика Казахстан', 'KAZ', '398');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440019', 'Республика Камерун', 'CMR', '120');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544001a', 'Республика Кения', 'KEN', '404');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544001b', 'Республика Кипр', 'CYP', '196');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544001c', 'Республика Кирибати', 'KIR', '296');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544001d', 'Республика Колумбия', 'COL', '170');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544001e', 'Республика Конго', 'COG', '178');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544001f', 'Республика Корея', 'KOR', '410');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440020', 'Республика Коста-Рика', 'CRI', '188');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440022', 'Республика Куба', 'CUB', '192');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440023', 'Республика Либерия', 'LBR', '430');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440024', 'Республика Маврикий', 'MUS', '480');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440025', 'Республика Мадагаскар', 'MDG', '450');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440026', 'Республика Малави', 'MWI', '454');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440027', 'Республика Мали', 'MLI', '466');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440028', 'Республика Мальта', 'MLT', '470');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440029', 'Республика Маршалловы Острова', 'MHL', '584');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544002a', 'Республика Мозамбик', 'MOZ', '508');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544002b', 'Республика Молдова', 'MDA', '498');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544002c', 'Республика Намибия', 'NAM', '516');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544002d', 'Республика Науру', 'NRU', '520');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544002e', 'Республика Нигер', 'NER', '562');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544002f', 'Республика Никарагуа', 'NIC', '558');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440030', 'Республика Палау', 'PLW', '585');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440031', 'Республика Панама', 'PAN', '591');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440032', 'Республика Парагвай', 'PRY', '600');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440033', 'Республика Перу', 'PER', '604');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440034', 'Республика Польша', 'POL', '616');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440035', 'Республика Сан-Марино', 'SMR', '674');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440036', 'Республика Северная Македония', 'MKD', '807');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440037', 'Республика Сейшелы', 'SYC', '690');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440038', 'Республика Сенегал', 'SEN', '686');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440039', 'Республика Сербия', 'SRB', '688');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544003a', 'Республика Сингапур', 'SGP', '702');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544003b', 'Республика Словения', 'SVN', '705');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544003c', 'Республика Союза Мьянма', 'MMR', '104');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544003d', 'Республика Судан', 'SDN', '729');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544003e', 'Республика Суринам', 'SUR', '740');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544003f', 'Республика Сьерра-Леоне', 'SLE', '694');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440040', 'Республика Таджикистан', 'TJK', '762');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440041', 'Республика Тринидад и Тобаго', 'TTO', '780');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440042', 'Республика Уганда', 'UGA', '800');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440043', 'Республика Узбекистан', 'UZB', '860');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440044', 'Республика Фиджи', 'FJI', '242');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440045', 'Республика Филиппины', 'PHL', '608');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440046', 'Республика Хорватия', 'HRV', '191');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440047', 'Республика Чад', 'TCD', '148');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440048', 'Республика Чили', 'CHL', '152');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440049', 'Республика Эквадор', 'ECU', '218');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544004a', 'Республика Экваториальная Гвинея', 'GNQ', '226');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544004b', 'Республика Эль-Сальвадор', 'SLV', '222');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544004c', 'Республика Южная Осетия', 'OST', '896');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544004d', 'Республика Южный Судан', 'SSD', '728');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544004e', 'Реюньон', 'REU', '638');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440050', 'Руандийская Республика', 'RWA', '646');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440051', 'Румыния', 'ROU', '642');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440052', 'Святая Елена, Остров Вознесения, Тристан-да-Кунья', 'SHN', '654');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440053', 'Сен-Бартелеми', 'BLM', '652');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440054', 'Сен-Мартен (нидерландская часть)', 'SXM', '534');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440055', 'Сен-Мартен (французская часть)', 'MAF', '663');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440056', 'Сент-Винсент и Гренадины', 'VCT', '670');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440057', 'Сент-Китс и Невис', 'KNA', '659');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440058', 'Сент-Люсия', 'LCA', '662');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440059', 'Сент-Пьер и Микелон', 'SPM', '666');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544005a', 'Сирийская Арабская Республика', 'SYR', '760');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544005b', 'Словацкая Республика', 'SVK', '703');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544005c', 'Содружество Багамы', 'BHS', '044');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544005d', 'Содружество Доминики', 'DMA', '212');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544005e', 'Содружество Северных Марианских островов', 'MNP', '580');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440061', 'Соломоновы острова', 'SLB', '090');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440062', 'Социалистическая Республика Вьетнам', 'VNM', '704');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440063', 'Союз Коморы', 'COM', '174');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440064',
        'Специальный административный район Гонконг Китайской Народной Республики', 'HKG', '344');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440065',
        'Специальный административный район Макао Китайской Народной Республики', 'MAC', '446');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440066', 'Султанат Оман', 'OMN', '512');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440067', 'Тайвань (Китай)', 'TWN', '158');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440068', 'Тоголезская Республика', 'TGO', '768');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440069', 'Токелау', 'TKL', '772');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544006a', 'Тувалу', 'TUV', '798');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544006b', 'Тунисская Республика', 'TUN', '788');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544006c', 'Турецкая Республика', 'TUR', '792');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544006d', 'Туркменистан', 'TKM', '795');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544006e', 'Украина', 'UKR', '804');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544006f', 'Уоллис и Футуна', 'WLF', '876');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440070', 'Фарерские острова', 'FRO', '234');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440071', 'Федеративная Демократическая Республика Эфиопия', 'ETH', '231');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440072', 'Федеративная Республика Бразилия', 'BRA', '076');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440074', 'Федеративная Республика Нигерия', 'NGA', '566');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440075', 'Федеративная Республика Сомали', 'SOM', '706');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440076', 'Федеративные Штаты Микронезии', 'FSM', '583');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440077', 'Финляндская Республика', 'FIN', '246');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440078', 'Фолклендские острова (Мальвинские)', 'FLK', '238');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440079', 'Французская Гвиана', 'GUF', '254');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544007a', 'Французская Полинезия', 'PYF', '258');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544007c', 'Французские Южные территории', 'ATF', '260');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544007d', 'Центрально-Африканская Республика', 'CAF', '140');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544007e', 'Черногория', 'MNE', '499');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-44665544007f', 'Чешская Республика', 'CZE', '203');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440080', 'Швейцарская Конфедерация', 'CHE', '756');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440081', 'Шпицберген и Ян Майен', 'SJM', '744');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440082', 'Эландские острова', 'ALA', '248');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440083', 'Эстонская Республика', 'EST', '233');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440084', 'Южная Джорджия и Южные Сандвичевы острова', 'SGS', '239');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440085', 'Южно-Африканская Республика', 'ZAF', '710');
INSERT INTO country (id, name, short_name, code)
VALUES ('550e8400-e29b-41d4-a716-446655440086', 'Ямайка', 'JAM', '388');

INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('китовый ус', 'BAL', 'кг', 'количество', 'китовый ус');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кора', 'BAR', 'кг', 'кора дерева', 'сырая, высушенная или порошкообразная; необработанная');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('тело', 'BOD', 'количество', 'кг', 'по существу целые мертвые животные');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кость', 'BON', 'кг', 'количество', 'кости, в том числе челюсти');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('калипи', 'CAL', 'кг', 'калипи или калипаш', 'черепашьи хрящи для супа');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('панцирь черепахи', 'CAP', 'количество', 'кг', 'сырые или необработанные целые панцири');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('резные изделия', 'CAR', 'кг', 'количество', 'резные изделия кроме слоновой кости');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('икра', 'CAV', 'кг', 'неоплодотворенные яйца', 'обработанные яйца всех видов');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('щепа', 'CHP', 'кг', 'щепа древесины', 'особенно Aquilaria spp., Gyrinops spp., Pterocarpus santalinus');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('коготь', 'CLA', 'количество', 'кг', 'когти, например, Felidae, Ursidae, Crocodylia');

INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('китовый ус', 'BAL', 'кг', 'количество', 'китовый ус');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кора', 'BAR', 'кг', 'кора дерева', 'сырая, высушенная или порошкообразная; необработанная');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('тело', 'BOD', 'количество', 'кг', 'по существу целые мертвые животные, включая целые рыбы, чучела и охотничьи трофеи');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кость', 'BON', 'кг', 'количество', 'кости, в том числе челюсти');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('калипи', 'CAL', 'кг', 'калипи или калипаш', 'черепашьи хрящи для супа');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('панцирь черепахи', 'CAP', 'количество', 'кг', 'сырые или необработанные целые панцири видов отряда Testudines');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('резные изделия', 'CAR', 'кг', 'количество', 'резные изделия, кроме слоновой кости, кости или рога, например, кораллы и дерево');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('резные изделия из кости', 'BOC', 'кг', 'количество', 'резные изделия из кости');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('резные изделия из рога', 'HOC', 'кг', 'количество', 'резные изделия из рога');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('резные изделия из слоновой кости', 'IVC', 'кг', 'количество', 'резные изделия из слоновой кости, включая ручки ножей и шахматные наборы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('икра', 'CAV', 'кг', 'мертвые обработанные яйца', 'неоплодотворенные яйца всех видов Acipenseriformes');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('щепа', 'CHP', 'кг', 'щепа древесины', 'особенно Aquilaria spp., Gyrinops spp., и Pterocarpus santalinus');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('коготь', 'CLA', 'количество', 'кг', 'когти, например, Felidae, Ursidae, или Crocodylia');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('ткань', 'CLO', 'м2', 'кг', 'ткань, если не полностью сделана из волос вида СИТЕС');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('коралл (необработанный)', 'COR', 'количество', 'кг', 'необработанные кораллы и коралловые породы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('косметика', 'COS', 'г/мл', 'количество', 'продукты, которые наносятся на внешнюю часть тела для чистки или изменения внешнего вида');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('культуры искусственно размноженных растений', 'CUL', 'кол-во', '', 'искусственно размноженные культуры растений');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('деривативы', 'DER', 'кг/л', 'количество', 'производные растений и животных, кроме указанных в других разделах');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('высушенное растение', 'DPL', 'количество', 'высушенные растения', 'например, гербарные образцы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('ухо', 'EAR', 'количество', 'кг', 'уши, обычно слоновьи');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('яйцо', 'EGG', 'количество', 'кг', 'целые неживые или выдутые яйца');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('яйцо (живое)', 'EGL', 'количество', 'кг', 'живые оплодотворенные яйца');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('яичная скорлупа', 'ESH', 'г/кг', 'количество', 'сырая или необработанная яичная скорлупа');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('экстракт', 'EXT', 'кг/л', '', 'растительные экстракты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('перо', 'FEA', 'кг', 'кол-во', 'перья и крылья, включая изделия из перьев');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('волокно', 'FIB', 'кг/м', '', 'натуральное волокно животного или растительного происхождения');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('плавник (сушенный)', 'FIN', 'кг', '', 'сушеные плавники и части плавников');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('плавник (сырой)', 'FFN', 'кг', '', 'свежие, охлажденные или замороженные плавники и части плавников');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('сеголетки', 'FIG', 'количество', 'кг', 'живая молодь рыбы для аквариумной торговли');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('цветок', 'FLO', 'кг', '', 'цветы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('цветочный горшок', 'FPT', 'количество', '', 'цветочные горшки, изготовленные из частей растений');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('лягушачьи лапки', 'LEG', 'кг', 'количество', 'лягушачьи лапки');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('фрукт', 'FRU', 'кг', '', 'фрукты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('нога', 'FOO', 'количество', 'кг', 'ноги слонов, носорогов, бегемотов и других крупных животных');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('меховые изделия (крупные)', 'FPL', 'количество', '', 'крупные промышленные изделия из меха');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('меховое изделие (мелкое)', 'FPS', 'количество', '', 'мелкие промышленные изделия из меха, включая сумки, кошельки и отделку одежды');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('желчь', 'GAL', 'кг', 'количество', 'желчь');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('желчный пузырь', 'GAB', 'количество', 'кг', 'желчные пузыри');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('предмет одежды', 'GAR', 'количество', '', 'предметы одежды, включая перчатки, головные уборы, но не обувь');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('гениталии', 'GEN', 'количество', 'кг', 'высушенные гениталии животных');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('жаберные пластины', 'GIL', 'кг', 'количество', 'жаберные пластины акул');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('шерсть', 'HAI', 'кг', '', 'включает всю шерсть животных, таких как слон, як, гуанако');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('продукты из шерсти', 'HAP', 'количество', 'g', 'изделия из шерсти, такие как браслеты из слоновьей шерсти');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('рог', 'HOR', 'количество', 'кг', 'рога, включая панты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('ювелирные изделия', 'JWL', 'количество', 'g', 'ювелирные изделия из других материалов, кроме слоновой кости');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('украшения – слоновая кость', 'IJW', 'количество', 'g', 'украшения из слоновой кости, включая амулеты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кожаное изделие (крупное)', 'LPL', 'количество', '', 'крупные изделия из кожи, такие как мебель и чемоданы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кожаное изделие (маленькое)', 'LPS', 'количество', '', 'мелкие изделия из кожи, такие как ремни, брелоки, сумки');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('живые животные и растения', 'LIV', 'количество', 'кг', 'живые животные и растения, кроме живых мальков рыб');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('лист', 'LVS', 'кг', 'количество', 'листья растений');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('бревна/балки', 'LOG', 'м3', '', 'необработанная древесина');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('мясо', 'MEA', 'кг', '', 'мясо, свежее или обработанное, включая копченое, сырое, сушеное, замороженное');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('лекарство', 'MED', 'кг', 'l', 'лекарственные продукты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('мускус', 'MUS', 'g', '', 'мускус');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('масло', 'OIL', 'кг', 'l', 'масло животных или растений, таких как черепахи, киты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('жемчуг', 'PRL', 'количество', '', 'жемчуг');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('клавиши пианино', 'KEY', 'количество', '', 'клавиши пианино из слоновой кости');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кусок – кость', 'BOP', 'кг', '', 'необработанные куски кости');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кусок – рог', 'HOP', 'кг', '', 'необработанные куски рога');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кусок – слоновая кость', 'IVP', 'кг', '', 'необработанные куски слоновой кости');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('пластины', 'PLA', 'м2', '', 'пластины из меховых шкур, включая ковры');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('фанера', 'PLY', 'м2', 'м3', 'материал из нескольких слоев древесины');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('порошок', 'POW', 'кг', '', 'сухое твердое вещество в виде мелких или крупных частиц');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('куколки', 'PUP', 'количество', '', 'куколки бабочек');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('корень', 'ROO', 'количество', 'кг', 'корни, луковицы, клубнелуковицы или клубни');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('ковёр', 'RUG', 'количество', '', 'ковры');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('рострум рыбы-пилы', 'ROS', 'количество', 'кг', 'рострум рыбы-пилы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('пиломатериалы', 'SAW', 'м3', '', 'древесина, просто распиленная вдоль');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('чешуя', 'SCA', 'кг', '', 'чешуя животных, таких как черепахи и рыбы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('семя', 'SEE', 'количество', 'кг', 'семена');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('раковина', 'SHE', 'количество', 'кг', 'сырая или необработанная раковина моллюсков');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('сторона', 'SID', 'количество', '', 'стороны или бока шкур');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('скелет', 'SKE', 'количество', '', 'практически целые скелеты');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кожа', 'SKI', 'количество', '', 'цельные шкуры, сырые или дубленые');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('кусочек кожи', 'SKP', 'кг', '', 'кусочки кожи, включая обрезки');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('череп', 'SKU', 'количество', '', 'черепа');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('суп', 'SOU', 'кг/л', '', 'суп, например, из черепахи');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('образец (научный)', 'SPE', 'кг/л/мл', 'количество', 'научные образцы, включая кровь и ткани');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('стебель', 'STE', 'количество', 'кг', 'стебли растений');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('плавательный пузырь', 'SWI', 'кг', '', 'гидростатический орган рыб');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('хвост', 'TAI', 'количество', 'кг', 'хвосты, включая лисы и кайманы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('зуб', 'TEE', 'количество', 'кг', 'зубы животных, таких как киты и львы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('древесина', 'TIM', 'м3', 'кг', 'необработанная древесина, кроме пиломатериалов');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('трансформированная древесина', 'TRW', 'м3', 'кг', 'древесина непрерывной формы');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('трофей', 'TRO', 'количество', '', 'трофейные части одного животного');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('хобот', 'TRU', 'количество', 'кг', 'хобот слона, экспортируемый как часть охотничьего трофея');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('бивень (необработанная слоновая кость)', 'TUS', 'количество', 'кг', 'целые бивни, включая бивни слона, бегемота, моржа и нарвала');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('листы шпона (ротационный)', 'VEN', 'м3', 'кг', 'тонкие слои древесины толщиной 6 мм или менее');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('листы шпона (строганный)', 'VEN', 'м3', 'кг', 'строганный шпон для производства фанеры и облицовки мебели');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('воск', 'WAX', 'кг', '', 'воск');
INSERT INTO products (description, code, preferred_unit, alternative_block, explanation) VALUES ('изделия из дерева', 'WPR', 'количество', 'кг', 'производственные изделия из дерева, включая мебель и музыкальные инструменты');
