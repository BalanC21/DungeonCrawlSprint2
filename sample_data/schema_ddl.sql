DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state
(
    id          serial                    NOT NULL PRIMARY KEY,
    name        text                      NOT NULL,
    current_map text                      NOT NULL,
    saved_at    date DEFAULT CURRENT_DATE NOT NULL
);

DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player
(
    id          serial  NOT NULL PRIMARY KEY,
    player_name text    NOT NULL,
    hp          integer NOT NULL,
    attack      integer NOT NULL,
    x           integer NOT NULL,
    y           integer NOT NULL
);

DROP TABLE IF EXISTS public.enemy;
CREATE TABLE public.enemy
(
    id         serial  NOT NULL PRIMARY KEY,
    save_name  text    NOT NULL,
    enemy_type text    NOT NULL,
    hp         integer NOT NULL,
    x          integer NOT NULL,
    y          integer NOT NULL
);

DROP TABLE IF EXISTS public.items;
CREATE TABLE public.items
(
    id             serial NOT NULL PRIMARY KEY,
    save_name      text   NOT NULL,
    inventory_type text   NOT NULL
);

