INSERT INTO item_to_process (id, objet_id)
    SELECT
        public.uuid_generate_v4(),
        p.id
    FROM
        portefeuille_cheque as p
        INNER JOIN lot_recouvrement_cheque as l ON l.id = p.lot_recouvrement_cheque_id
    WHERE
        p.ind_comptabilise is not true
        AND (
                p.statut_cheque = :statutPaye
                OR (p.statut_cheque = :statutEncours AND l.date_denouement >= :dateTraitement)
                OR (p.statut_cheque = :statutImpaye AND p.ind_forcage_compta_impayes is true)
            )
ON CONFLICT (objet_id) DO NOTHING;