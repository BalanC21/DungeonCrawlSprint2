DROP TABLE IF EXISTS public.enemies;
CREATE TABLE public.enemies
(
    id            serial  NOT NULL PRIMARY KEY,
    game_state_id integer NOT NULL,
    enemy_type    text    NOT NULL,
    hp            integer NOT NULL,
    x             integer NOT NULL,
    y             integer NOT NULL
);

DROP TABLE IF EXISTS public.items;
CREATE TABLE public.items
(
    id            serial  NOT NULL PRIMARY KEY,
    game_state_id integer NOT NULL,
    item_type     text    NOT NULL,
    x             integer NOT NULL,
    y             integer NOT NULL
);



DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state
(
    id          serial                    NOT NULL PRIMARY KEY,
    name        text                      NOT NULL,
    current_map text                      NOT NULL,
    saved_at    date DEFAULT CURRENT_DATE NOT NULL,
    player_id   integer                   NOT NULL
);


DROP TABLE IF EXISTS public.player;
CREATE TABLE public.player
(
    id          serial  NOT NULL PRIMARY KEY,
    player_name text    NOT NULL,
    hp          integer NOT NULL,
    x           integer NOT NULL,
    y           integer NOT NULL,
    inventory   text    NOT NULL,
    attack      int     NOT NULL

);



ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player (id);

ALTER TABLE ONLY public.enemies
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state (id);

ALTER TABLE ONLY public.items
    ADD CONSTRAINT fk_game_state_id FOREIGN KEY (game_state_id) REFERENCES public.game_state (id);