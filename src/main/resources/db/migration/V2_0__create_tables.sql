create table disk_drive
(
    id       varchar(255) not null
        primary key,
    capacity varchar(255),
    name     varchar(255),
    type     varchar(255) not null
);

create table laptop
(
    id                   bigint           not null
        primary key,
    accumulator_capacity integer          not null,
    diagonal             double precision not null,
    refresh_rate         integer          not null,
    screen_resolution    varchar(255),
    web_cam_resolution   varchar(255),
    weight               real             not null
);

create table person
(
    id           varchar(255) not null
        primary key,
    address      varchar(255),
    city         varchar(255),
    email        varchar(255),
    name         varchar(255),
    password     varchar(255),
    phone_number varchar(255),
    role         varchar(255)
);

create table invoice
(
    id             varchar(255) not null
        primary key,
    checked        boolean      not null,
    date_created   date,
    invoice_status varchar(255),
    person_id      varchar(255)
        constraint fkmhvmee8jxfysywy9xqpobqbfj
            references person
);

create table processor
(
    id           varchar(255) not null
        primary key,
    frequency    varchar(255),
    manufacturer varchar(255) not null,
    model        varchar(255)
);

create table telephone
(
    id                varchar(255)     not null
        primary key,
    bluetooth         varchar(255),
    camera            varchar(255),
    color             varchar(255),
    diagonal          double precision not null,
    internal_memory   varchar(255),
    material          varchar(255),
    number_ofsim      integer          not null,
    operating_system  varchar(255),
    pixel_density     integer          not null,
    price             integer          not null,
    ram               integer          not null,
    refresh_rate      integer          not null,
    screen_resolution varchar(255),
    screen_type       varchar(255),
    sim_slot_type     varchar(255),
    socket            varchar(255),
    wifi              varchar(255),
    processor_id      varchar(255)
        constraint fk71yqd7pedqrr8gc3suj4apj02
            references processor
);

create table video_card
(
    id           varchar(255) not null
        primary key,
    manufacturer varchar(255),
    model        varchar(255),
    type         varchar(255) not null,
    vram         varchar(255)
);

create table computer
(
    id               varchar(255) not null
        primary key,
    description      varchar(255),
    manufacturer     varchar(255),
    model            varchar(255),
    operating_system varchar(255),
    price            integer      not null
        constraint computer_price_check
            check (price >= 0),
    ram              varchar(255),
    ram_type         varchar(255),
    diskdrive_id     varchar(255)
        constraint fk1hmshfm3b5bjtuwbwobda7boq
            references disk_drive,
    processor_id     varchar(255)
        constraint fk3jbp9x2se63ij7uswppbuiysx
            references processor,
    videocard_id     varchar(255)
        constraint fkr4y66kb15ptygr3r1mru9pybi
            references video_card
);

create table image
(
    id          varchar(255) not null
        primary key,
    bytes       bytea,
    computer_id varchar(255)
        constraint fkc9qedx7p1i00cobq2u6dhb8cn
            references computer
);

create table invoice_products
(
    invoice_id varchar(255) not null
        constraint fksyku4sthayo3edqg4haewq87b
            references invoice,
    quantity   integer,
    product_id varchar(255) not null
        constraint fkoq4n4k9k1gnrtaw1405eqab51
            references computer,
    primary key (invoice_id, product_id)
);

