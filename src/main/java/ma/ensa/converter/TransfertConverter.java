package ma.ensa.converter;

import ma.ensa.dto.TransfertDTO;
import ma.ensa.model.Transfert;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.stereotype.Component;

@Component
public class TransfertConverter extends AbstractConverter<Transfert, TransfertDTO> {

    private final ModelMapper modelMapper;

    public TransfertConverter(ModelMapper modelMapper) {
        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
        this.modelMapper = modelMapper;
    }

    @Override
    public Transfert convertToDM(TransfertDTO transfertDTO) {
        return modelMapper.map(transfertDTO, Transfert.class);
    }

    @Override
    public TransfertDTO convertToDTO(Transfert transfert) {
        return modelMapper.map(transfert, TransfertDTO.class);
    }
}
